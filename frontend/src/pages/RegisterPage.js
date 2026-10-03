import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import AuthShell from '../components/AuthShell';
export default function RegisterPage() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (form.password.length < 6) {
      setError('Use at least 6 characters for your password.');
      return;
    }
    setLoading(true);
    try {
    await register({
  username: form.name.trim(),
  email: form.email.trim(),
  password: form.password,
  fullName: form.name.trim()
});
      navigate('/'); // AUTH (keep your original redirect)
    } catch (err) {
      setError(err?.response?.data?.message || 'We could not create your account. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return(<AuthShell
      title="Create your account"
      subtitle="Save recipes and search by what is in your kitchen."
      footer={<>Already have an account? <Link to="/login">Sign in</Link></>}>
      <form onSubmit={onSubmit} className="form" noValidate>
        {error && <div className="alert" role="alert">{error}</div>}
        <label htmlFor="name">Name</label>
        <input id="name" name="name" type="text" autoComplete="name" required value={form.name} onChange={onChange}/>
        <label htmlFor="email">Email</label>
        <input id="email" name="email" type="email" autoComplete="email" required value={form.email} onChange={onChange}/>
        <labelhtmlFor="password">Password</label> 
        <input id="password" name="password" type="password" autoComplete="new-password" required value={form.password} onChange={onChange}/>
        <button className="btn btn-primary btn-lg" disabled={loading}>{loading ? 'Creating account…' : 'Create Account'}</button>
      </form>
    </AuthShell>);
}