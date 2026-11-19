import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { recipeService } from '../services/api';
import RecipeCard from '../components/RecipeCard';
import { Heart } from '../components/Icons';
import { toList } from '../utils/recipeFields';

export default function BookmarksPage() {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const loadSavedRecipes = async () => {
    setLoading(true);
    setError('');

    // 1. Try fetching from Spring Boot backend
    try {
      const res = await recipeService.getBookmarks();
      const list = toList(res);
      if (list && list.length > 0) {
        setRecipes(list);
        setLoading(false);
        return;
      }
    } catch (err) {
      console.warn('Backend bookmarks endpoint not reached, checking local storage...');
    }

    // 2. Fallback: Load locally saved recipes from storage
    try {
      const localSaved = localStorage.getItem('user_saved_recipes');
      if (localSaved) {
        setRecipes(JSON.parse(localSaved));
      }
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSavedRecipes();
  }, []);

  const handleRemove = async (recipe) => {
    // 1. Remove from backend
    try {
      await recipeService.toggleBookmark(recipe.id);
    } catch (e) {
      console.warn('Could not remove from backend');
    }

    // 2. Remove from local state and storage
    setRecipes((prev) => {
      const updated = prev.filter((r) => r.id !== recipe.id);
      localStorage.setItem('user_saved_recipes', JSON.stringify(updated));
      return updated;
    });
  };

  return (
    <main className="container results page-top">
      <div className="results-header">
        <div>
          <h1 className="results-title">
            <Heart filled width={26} height={26} style={{ color: '#ef4444', verticalAlign: 'middle', marginRight: 8 }} />
            Saved Recipes
          </h1>
          <p className="results-subtitle">All your favorited recipes saved in one place</p>
        </div>
        {!loading && recipes.length > 0 && (
          <div className="count-pill">
            <strong>{recipes.length}</strong> saved
          </div>
        )}
      </div>

      {error && (
        <div className="status-state state-error" role="alert">
          <p>{error}</p>
        </div>
      )}

      {loading && (
        <div className="recipe-grid" aria-busy="true">
          {[1, 2, 3].map((i) => (
            <div key={i} className="recipe-skeleton-card">
              <div className="skeleton-image" />
              <div className="skeleton-body">
                <div className="skeleton-line title" />
                <div className="skeleton-line subtitle" />
              </div>
            </div>
          ))}
        </div>
      )}

      {!loading && recipes.length === 0 && !error && (
        <div className="status-state state-empty">
          <div className="state-icon">❤️</div>
          <h3>Nothing saved yet</h3>
          <p>Tap the heart icon on any recipe on the home page to keep it here!</p>
          <Link to="/" className="btn btn-primary">
            Browse Recipes
          </Link>
        </div>
      )}

      {!loading && recipes.length > 0 && (
        <div className="recipe-grid">
          {recipes.map((r) => (
            <RecipeCard key={r.id} recipe={r} bookmarked onBookmark={handleRemove} />
          ))}
        </div>
      )}
    </main>
  );
}