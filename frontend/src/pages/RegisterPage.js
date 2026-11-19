import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import AuthShell from '../components/AuthShell';

export default function RegisterPage() {
  const { register, login } = useAuth(); // <--- 1. 'login' function add kiya
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Email format check
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(form.email.trim())) {
      setError('Please enter a valid email address (e.g. name@gmail.com).');
      return;
    }

    if (form.password.length < 6) {
      setError('Use at least 6 characters for your password.');
      return;
    }

    setLoading(true);
    try {
      const cleanEmail = form.email.trim().toLowerCase();
      const cleanName = form.name.trim();

      // 1. Account create hoga
      await register({
        username: cleanEmail,
        email: cleanEmail,
        password: form.password,
        fullName: cleanName,
      });

      // 2. ✨ AUTO-LOGIN: Usi waqt background me sign-in ho jayega
      await login({

        email: cleanEmail,
        password: form.password,
      });

      // 3. ✨ Direct Homepage par redirect!
      navigate('/');
    } catch (err) {
      setError(
        err?.response?.data?.message ||
        'We could not create your account. Please try again.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthShell
      title="Create your account"
      subtitle="Save recipes and search by what is in your kitchen."
      footer={
        <>
          Already have an account? <Link to="/login">Sign in</Link>
        </>
      }
    >
      <form onSubmit={onSubmit} className="auth-form" noValidate autoComplete="off">
        {error && <div className="alert-error" role="alert">{error}</div>}

        {/* Hidden inputs to stop browser autofill */}
        <input type="text" style={{ display: 'none' }} tabIndex={-1} />
        <input type="password" style={{ display: 'none' }} tabIndex={-1} />

        <div className="form-group">
          <label htmlFor="name">Full Name</label>
          <input
            id="name"
            name="name"
            type="text"
            required
            autoComplete="off"
            value={form.name}
            onChange={onChange}
            placeholder="Enter your name"
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            id="email"
            name="email"
            type="email"
            required
            autoComplete="new-password"
            value={form.email}
            onChange={onChange}
            placeholder="Enter your email"
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            id="password"
            name="password"
            type="password"
            required
            autoComplete="new-password"
            value={form.password}
            onChange={onChange}
            placeholder="••••••••"
          />
        </div>

        <button className="btn btn-primary btn-lg btn-block" disabled={loading}>
          {loading ? 'Creating Account…' : 'Create Account'}
        </button>
      </form>
    </AuthShell>
  );
}