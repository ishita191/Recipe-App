import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { recipeService } from '../services/api';
import { useAuth } from '../context/AuthContext';
import RecipeCard from '../components/RecipeCard';
import { SearchIcon, Plus, Close } from '../components/Icons';
import { toList, getImage, isSaved } from '../utils/recipeFields';

/*
  ADAPT (service method names): this page assumes
    recipeService.getAll()                       -> GET /recipes
    recipeService.search(query)                  -> GET /recipes/search?query=...
    recipeService.searchByIngredients(csv)       -> GET /recipes/search/ingredients?ingredients=a,b
    recipeService.toggleBookmark(id)             -> POST /recipes/{id}/bookmark
  If your api.js names differ, change only the four calls marked  // API.
*/
export default function HomePage() {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [query, setQuery] = useState('');
  const [ingredientInput, setIngredientInput] = useState('');
  const [ingredients, setIngredients] = useState([]);
  const [cuisine, setCuisine] = useState('');
  const [category, setCategory] = useState('');
  const [savedIds, setSavedIds] = useState(() => new Set());

  const load = useCallback(async (request) => {
    setLoading(true);
    setError('');
    try {
      const res = await request();
      const list = toList(res);
      setRecipes(list);
      setSavedIds(new Set(list.filter(isSaved).map((r) => r.id)));
    } catch (e) {
      setError(e?.response?.data?.message || 'We could not load recipes. Check your connection and try again.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load(() => recipeService.getAll()); // API
  }, [load]);

  const addIngredient = () => {
    const parts = ingredientInput.split(',').map((s) => s.trim()).filter(Boolean);
    if (!parts.length) return;
    setIngredients((prev) => [...new Set([...prev, ...parts])]);
    setIngredientInput('');
  };

  const removeIngredient = (name) => setIngredients((prev) => prev.filter((i) => i !== name));

  const handleSearch = (e) => {
    e?.preventDefault();
    const pending = ingredientInput.split(',').map((s) => s.trim()).filter(Boolean);
    const all = [...new Set([...ingredients, ...pending])];
    setIngredients(all);
    setIngredientInput('');

    if (all.length) load(() => recipeService.searchByIngredients(all.join(','))); // API
    else if (query.trim()) load(() => recipeService.search(query.trim())); // API
    else load(() => recipeService.getAll()); // API
    document.getElementById('recipes')?.scrollIntoView({ behavior: 'smooth' });
  };

  const hasFilters = Boolean(query || ingredients.length || cuisine || category);

  const clearFilters = () => {
    setQuery('');
    setIngredients([]);
    setIngredientInput('');
    setCuisine('');
    setCategory('');
    load(() => recipeService.getAll()); // API
  };

  const handleBookmark = async (recipe) => {
    if (!user) return navigate('/login');
    try {
      await recipeService.toggleBookmark(recipe.id); // API
      setSavedIds((prev) => {
        const next = new Set(prev);
        next.has(recipe.id) ? next.delete(recipe.id) : next.add(recipe.id);
        return next;
      });
    } catch (e) {
      setError('Could not update your saved recipes. Please try again.');
    }
  };

  // Filter options come from the loaded recipes, so nothing is hardcoded.
  const cuisines = useMemo(() => [...new Set(recipes.map((r) => r.cuisine).filter(Boolean))].sort(), [recipes]);
  const categories = useMemo(() => [...new Set(recipes.map((r) => r.category).filter(Boolean))].sort(), [recipes]);

  const visible = recipes.filter(
    (r) => (!cuisine || r.cuisine === cuisine) && (!category || r.category === category)
  );
  const heroImage =
  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-5H4YY5yoN0FCMf5dX9iFqotP4omMFzp6dSv4jeIQMP0cYt4jiOQxUaSk&s=10";
  return (
    <main>
      <section className="hero">
        <div className="container hero-grid">
          <div className="hero-copy">
            <h1>Crave It. Find It. Cook It.</h1>
            <p className="lead">Explore tasty recipes for every craving and every occasion.</p>

            <form className="search-panel" onSubmit={handleSearch}>
              <label className="field">
                <SearchIcon />
                <input
                  type="search"
                  placeholder="Search recipes by name"
                  value={query}
                  onChange={(e) => setQuery(e.target.value)}
                  aria-label="Search recipes by name"
                />
              </label>

              <div className="field-row">
                <label className="field">
                  <input
                    type="text"
                    placeholder="Add an ingredient"
                    value={ingredientInput}
                    onChange={(e) => setIngredientInput(e.target.value)}
                    onKeyDown={(e) => {
                      if (e.key === 'Enter') {
                        e.preventDefault();
                        addIngredient();
                      }
                    }}
                    aria-label="Add an ingredient"
                  />
                </label>
                <button type="button" className="btn btn-soft" onClick={addIngredient}>
                  <Plus width={16} height={16} /> Add
                </button>
              </div>

              {ingredients.length > 0 && (
                <ul className="chips" aria-label="Selected ingredients">
                  {ingredients.map((name) => (
                    <li key={name} className="chip">
                      {name}
                      <button type="button" aria-label={`Remove ${name}`} onClick={() => removeIngredient(name)}>
                        <Close width={14} height={14} />
                      </button>
                    </li>
                  ))}
                </ul>
              )}

              <div className="filter-row">
                <select value={cuisine} onChange={(e) => setCuisine(e.target.value)} aria-label="Filter by cuisine">
                  <option value="">All cuisines</option>
                  {cuisines.map((c) => <option key={c} value={c}>{c}</option>)}
                </select>
                <select value={category} onChange={(e) => setCategory(e.target.value)} aria-label="Filter by category">
                  <option value="">All categories</option>
                  {categories.map((c) => <option key={c} value={c}>{c}</option>)}
                </select>
                {hasFilters && (
                  <button type="button" className="btn btn-ghost" onClick={clearFilters}>Clear filters</button>
                )}
              </div>

              <button type="submit" className="btn btn-primary btn-lg">Find Recipes</button>
            </form>
          </div>

          <div className="hero-media" aria-hidden="true">
            {heroImage ? <img src={heroImage} alt="" /> : <div className="hero-placeholder" />}
          </div>
        </div>
      </section>

      <section id="recipes" className="container results">
        <div className="section-head">
          <h2>{hasFilters ? 'Search results' : 'Popular recipes'}</h2>
          {!loading && !error && <span className="count">{visible.length} {visible.length === 1 ? 'recipe' : 'recipes'}</span>}
        </div>

        {error && (
          <div className="state state-error" role="alert">
            <h3>Something went wrong</h3>
            <p>{error}</p>
            <button className="btn btn-primary" onClick={clearFilters}>Try again</button>
          </div>
        )}

        {loading && (
          <div className="grid" aria-busy="true">
            {Array.from({ length: 6 }).map((_, i) => <div key={i} className="skeleton-card" />)}
          </div>
        )}

        {!loading && !error && visible.length === 0 && (
          <div className="state">
            <h3>No recipes match yet</h3>
            <p>Try fewer ingredients or a different cuisine.</p>
            <button className="btn btn-primary" onClick={clearFilters}>Clear filters</button>
          </div>
        )}

        {!loading && !error && visible.length > 0 && (
          <div className="grid">
            {visible.map((r) => (
              <RecipeCard key={r.id} recipe={r} bookmarked={savedIds.has(r.id)} onBookmark={handleBookmark} />
            ))}
          </div>
        )}
      </section>
    </main>
  );
}