import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import AuthShell from '../components/AuthShell';

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await login({
        email: form.email.trim().toLowerCase(),
        password: form.password,
      });

      navigate('/');
    } catch (err) {
      setError(
        err?.response?.data?.message ||
        'Incorrect email or password. Please try again.'
      );
    } finally {
      setLoading(false);
    }
  };
  return (
    <AuthShell
      title="Welcome back"
      subtitle="Sign in to access your saved recipes and meal plans."
      footer={
        <>
          New to CookMate? <Link to="/register">Create an account</Link>
        </>
      }
    >
      <form onSubmit={onSubmit} className="auth-form" noValidate>
        {error && <div className="alert-error" role="alert">{error}</div>}
        <div className="form-group">
          <label htmlFor="email">Email or Username</label>
          <input
            id="email"
            name="email"
            type="text"
            required
            autoComplete="off"
            value={form.email}
            onChange={onChange}
            placeholder="you@example.com"
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            id="password"
            name="password"
            type="password"
            required
            value={form.password}
            onChange={onChange}
            placeholder="••••••••"
          />
        </div>
        <button className="btn btn-primary btn-lg btn-block" disabled={loading}>
          {loading ? 'Signing in…' : 'Sign In'}
        </button>
      </form>
    </AuthShell>
  );
}