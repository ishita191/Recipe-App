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
  username: form.email.trim(),
  password: form.password
});
      navigate('/');
    } catch (err) {
      setError(err?.response?.data?.message || 'Incorrect email or password. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthShell
      title="Welcome back"
      subtitle="Sign in to see your saved recipes."
      footer={<>New to CookMate? <Link to="/register">Create an account</Link></>}
    >
      <form onSubmit={onSubmit} className="form" noValidate>
        {error && <div className="alert" role="alert">{error}</div>}
        <label htmlFor="email">Email</label>
        <input id="email" name="email" type="email" autoComplete="off" required value={form.email} onChange={onChange} />
        <label htmlFor="password">Password</label>
        <input id="password" name="password" type="password" autoComplete="new-password" required value={form.password} onChange={onChange} />
        <button className="btn btn-primary btn-lg" disabled={loading}>{loading ? 'Signing in…' : 'Sign In'}</button>
      </form>
    </AuthShell>
  );
}