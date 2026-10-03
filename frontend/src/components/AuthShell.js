import React from 'react';
import { Link } from 'react-router-dom';
import { ChefHat } from './Icons';

// Presentation-only wrapper shared by Login and Register.
export default function AuthShell({ title, subtitle, children, footer }) {
  return (
    <main className="auth">
      <aside className="auth-side">
        <Link to="/" className="brand brand-light"><ChefHat width={30} height={30} /><span>CookMate</span></Link>
        <h2>Cook with what you already have.</h2>
        <p>Search by ingredient, save your favorites and pick up where you left off.</p>
      </aside>
      <section className="auth-card">
        <h1>{title}</h1>
        <p className="muted">{subtitle}</p>
        {children}
        <p className="auth-footer">{footer}</p>
      </section>
    </main>
  );
}