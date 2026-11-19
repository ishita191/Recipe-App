import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { ChefHat } from './Icons';

export default function Footer() {
    const [email, setEmail] = useState('');
    const [subscribed, setSubscribed] = useState(false);

    const handleSubscribe = (e) => {
        e.preventDefault();
        if (email.trim()) {
            setSubscribed(true);
            setEmail('');
        }
    };

    return (
        <footer className="site-footer">
            <div className="container footer-container">
                {/* BRAND & TAGLINE */}
                <div className="footer-col brand-col">
                    <Link to="/" className="brand footer-brand">
                        <ChefHat width={28} height={28} />
                        <span>CookMate</span>
                    </Link>
                    <p className="footer-bio">
                        Turn what’s in your fridge into extraordinary homemade meals. Explore over 1,000+ chef-tested recipes tailored to your ingredients.
                    </p>
                    {/* <div className="footer-socials">
                        <span className="social-pill" title="Instagram">📸 Instagram</span>
                        <span className="social-pill" title="Pinterest">📌 Pinterest</span>
                        <span className="social-pill" title="YouTube">🎥 YouTube</span>
                    </div> */}
                </div>

                {/* QUICK NAVIGATION */}
                <div className="footer-col">
                    <h4>Explore</h4>
                    <ul className="footer-links">
                        <li><Link to="/">Discover Recipes</Link></li>
                        <li><a href="/#recipes">What's In Your Fridge?</a></li>
                        <li><Link to="/bookmarks">Saved Favorites</Link></li>
                        <li><a href="/#recipes">Quick 20-min Meals</a></li>
                    </ul>
                </div>

                {/* CUISINES */}
                <div className="footer-col">
                    <h4>Popular Cuisines</h4>
                    <ul className="footer-links">
                        <li><a href="/#recipes">Italian Pastas</a></li>
                        <li><a href="/#recipes">Mexican Tacos & Bowls</a></li>
                        <li><a href="/#recipes">Mediterranean Salads</a></li>
                        <li><a href="/#recipes">Asian Stir-Fries</a></li>
                    </ul>
                </div>

                {/* NEWSLETTER SIGNUP */}
                <div className="footer-col newsletter-col">
                    <h4>Weekly Recipe Drop</h4>
                    <p className="newsletter-desc">
                        Get 5 handpicked easy dinners sent to your inbox every Sunday.
                    </p>
                    {subscribed ? (
                        <div className="subscribed-success">
                            🎉 You're on the list! Check your inbox soon.
                        </div>
                    ) : (
                        <form onSubmit={handleSubscribe} className="footer-form">
                            <input
                                type="email"
                                placeholder="Enter your email"
                                required

                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                            <button type="submit" className="btn btn-primary btn-sm">
                                Join Free
                            </button>
                        </form>
                    )}
                </div>
            </div>

            {/* COPYRIGHT BOTTOM BAR */}
            <div className="footer-bottom">
                <div className="container bottom-inner">
                    <p>© {new Date().getFullYear()} CookMate Inc. Crafted with passion for home cooks.</p>
                    <div className="bottom-links">
                        <a href="#privacy">Privacy Policy</a>
                        <span>•</span>
                        <a href="#terms">Terms of Service</a>
                        <span>•</span>
                        <a href="#cookies">Cookie Settings</a>
                    </div>
                </div>
            </div>
        </footer>
    );
}