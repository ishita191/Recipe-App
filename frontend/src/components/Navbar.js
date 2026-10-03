import React, { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { ChefHat, Menu, Close } from './Icons';

// ADAPT: if useAuth() exposes different names (e.g. currentUser), change the line below.
export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

  const displayName = user?.name || user?.username || user?.fullName || user?.email || 'Account';
  const initial = String(displayName).trim().charAt(0).toUpperCase();
  const close = () => setOpen(false);

  const handleLogout = () => {
    close();
    logout();
    navigate('/login');
  };

  return (
    <header className="navbar">
      <div className="navbar-inner container">
        <Link to="/" className="brand" onClick={close}>
          <ChefHat width={28} height={28} />
          <span>CookMate</span>
        </Link>

        <button className="nav-toggle" aria-label="Toggle menu" aria-expanded={open} onClick={() => setOpen(!open)}>
          {open ? <Close /> : <Menu />}
        </button>

        <nav className={`nav-links ${open ? 'open' : ''}`}>
          <NavLink to="/" end onClick={close}>Discover</NavLink>
          <a
            href="/#recipes"
            onClick={(e) => {
              e.preventDefault();
              close();
              navigate('/');
              setTimeout(() => document.getElementById('recipes')?.scrollIntoView({ behavior: 'smooth' }), 80);
            }}
          >
            Recipes
          </a>
          <NavLink to="/bookmarks" onClick={close}>Saved Recipes</NavLink>

          <div className="nav-actions">
            {user ? (
              <>
                <span className="avatar" title={displayName}>{initial}</span>
                <span className="nav-username">{displayName}</span>
                <button className="btn btn-ghost btn-sm" onClick={handleLogout}>Sign Out</button>
              </>
            ) : (
              <>
                <Link to="/login" className="btn btn-ghost btn-sm" onClick={close}>Sign In</Link>
                <Link to="/register" className="btn btn-primary btn-sm" onClick={close}>Get Started</Link>
              </>
            )}
          </div>
        </nav>
      </div>
    </header>
  );
}