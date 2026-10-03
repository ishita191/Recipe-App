import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { recipeService } from '../services/api';
import RecipeCard from '../components/RecipeCard';
import { Heart } from '../components/Icons';
import { toList } from '../utils/recipeFields';

// Route protection stays in App.js (unchanged).
export default function BookmarksPage() {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;
    recipeService.getBookmarks() // API
      .then((res) => active && setRecipes(toList(res)))
      .catch(() => active && setError('We could not load your saved recipes. Please try again.'))
      .finally(() => active && setLoading(false));
    return () => { active = false; };
  }, []);

  const handleRemove = async (recipe) => {
    try {
      await recipeService.toggleBookmark(recipe.id); // API
      setRecipes((prev) => prev.filter((r) => r.id !== recipe.id));
    } catch (e) {
      setError('Could not remove this recipe. Please try again.');
    }
  };

  return (
    <main className="container results page-top">
      <div className="section-head">
        <h1 className="page-title"><Heart filled width={28} height={28} className="title-heart" /> Saved Recipes</h1>
        {!loading && recipes.length > 0 && <span className="count">{recipes.length} saved</span>}
      </div>

      {error && <div className="state state-error" role="alert"><p>{error}</p></div>}

      {loading && <div className="grid" aria-busy="true">{Array.from({ length: 3 }).map((_, i) => <div key={i} className="skeleton-card" />)}</div>}

      {!loading && recipes.length === 0 && !error && (
        <div className="state">
          <h3>Nothing saved yet</h3>
          <p>Tap the heart on any recipe to keep it here.</p>
          <Link to="/" className="btn btn-primary">Browse recipes</Link>
        </div>
      )}

      {!loading && recipes.length > 0 && (
        <div className="grid">
          {recipes.map((r) => <RecipeCard key={r.id} recipe={r} bookmarked onBookmark={handleRemove} />)}
        </div>
      )}
    </main>
  );
}