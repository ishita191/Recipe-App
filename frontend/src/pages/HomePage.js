import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState
} from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { recipeService } from '../services/api';
import { useAuth } from '../context/AuthContext';
import RecipeCard from '../components/RecipeCard';
import { SearchIcon, Plus, Close } from '../components/Icons';
import { toList, isSaved } from '../utils/recipeFields';

const POPULAR_INGREDIENTS = [
  'Garlic',
  'Tomato',
  'Pasta',
  'Cheese',
  'Lemon'
];

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
  const [searchTab, setSearchTab] = useState('all');

  const requestId = useRef(0);

  const load = useCallback(async (request) => {
    const currentRequest = ++requestId.current;

    setLoading(true);
    setError('');

    try {
      const res = await request();
      if (currentRequest !== requestId.current) return;

      const list = toList(res);
      setRecipes(list);
      setSavedIds(new Set(list.filter(isSaved).map((r) => r.id)));
    } catch (e) {
      if (currentRequest !== requestId.current) return;
      console.error('Failed to load recipes from backend:', e);
      setError(
        e?.response?.data?.message ||
        'Could not load recipes from http://localhost:8080/api/recipes. Please verify your backend is running.'
      );
    } finally {
      if (currentRequest === requestId.current) {
        setLoading(false);
      }
    }
  }, []);

  useEffect(() => {
    load(() => recipeService.getAll());
  }, [load]);

  const addIngredient = (customValue) => {
    const val = typeof customValue === 'string' ? customValue : ingredientInput;
    const parts = val.split(',').map((s) => s.trim()).filter(Boolean);
    if (!parts.length) return;
    setIngredients((prev) => [...new Set([...prev, ...parts])]);
    setIngredientInput('');
  };

  const removeIngredient = (name) => {
    setIngredients((prev) => prev.filter((i) => i !== name));
  };

  const handleSearch = (e) => {
    e?.preventDefault();
    const pending = ingredientInput.split(',').map((s) => s.trim()).filter(Boolean);
    const all = [...new Set([...ingredients, ...pending])];
    setIngredients(all);
    setIngredientInput('');

    if (all.length) {
      load(() => recipeService.searchByIngredients(all.join(',')));
    } else if (query.trim()) {
      load(() => recipeService.search(query.trim()));
    } else {
      load(() => recipeService.getAll());
    }

    document.getElementById('recipes')?.scrollIntoView({ behavior: 'smooth' });
  };

  const hasFilters = Boolean(query || ingredients.length || cuisine || category);

  const clearFilters = () => {
    setQuery('');
    setIngredients([]);
    setIngredientInput('');
    setCuisine('');
    setCategory('');
    load(() => recipeService.getAll());
  };

  const handleBookmark = async (recipe) => {
    if (!user) return navigate('/login');

    // 1. Instantly toggle heart UI state
    setSavedIds((prev) => {
      const next = new Set(prev);
      const isAlreadySaved = next.has(recipe.id);

      if (isAlreadySaved) {
        next.delete(recipe.id);
      } else {
        next.add(recipe.id);
      }

      // Sync with storage for Saved Recipes page
      try {
        const stored = JSON.parse(localStorage.getItem('user_saved_recipes') || '[]');
        let updated;
        if (isAlreadySaved) {
          updated = stored.filter((r) => r.id !== recipe.id);
        } else {
          updated = [...stored.filter((r) => r.id !== recipe.id), recipe];
        }
        localStorage.setItem('user_saved_recipes', JSON.stringify(updated));
      } catch (e) {
        console.error(e);
      }

      return next;
    });

    // 2. Sync with Spring Boot backend
    try {
      await recipeService.toggleBookmark(recipe.id);
    } catch (e) {
      console.warn('Backend bookmark sync pending, saved in local storage.');
    }
  };

  const cuisines = useMemo(
    () => [...new Set(recipes.map((r) => r.cuisine).filter(Boolean))].sort(),
    [recipes]
  );
  const categories = useMemo(
    () => [...new Set(recipes.map((r) => r.category).filter(Boolean))].sort(),
    [recipes]
  );

  const visible = recipes.filter(
    (r) => (!cuisine || r.cuisine === cuisine) && (!category || r.category === category)
  );

  const heroImage =
    'https://images.unsplash.com/photo-1543353071-10c8ba85a904?auto=format&fit=crop&w=1200&q=80';

  return (
    <main className="recipe-page">
      {/* HERO SECTION */}
      <section className="hero-section">
        <div className="container hero-container">
          <div className="hero-content">
            <span className="hero-badge">
              <span className="badge-pulse"></span>
              Over 1,000+ Homemade & Chef-Tested Recipes
            </span>

            <h1 className="hero-title">
              What are you <br />
              <span className="text-highlight">craving today?</span>
            </h1>

            <p className="hero-subtitle">
              Search by recipe name or enter the ingredients in your fridge to discover delicious meals in minutes.
            </p>

            {/* SEARCH PANEL */}
            <div className="search-card">
              <div className="search-tabs" role="tablist">
                <button
                  type="button"
                  className={`tab-btn ${searchTab === 'all' ? 'active' : ''}`}
                  onClick={() => setSearchTab('all')}
                >
                  🔍 By Recipe Name
                </button>
                <button
                  type="button"
                  className={`tab-btn ${searchTab === 'ingredients' ? 'active' : ''}`}
                  onClick={() => setSearchTab('ingredients')}
                >
                  🥦 What's In Your Fridge?
                  {ingredients.length > 0 && <span className="tab-counter">{ingredients.length}</span>}
                </button>
              </div>

              <form onSubmit={handleSearch} className="search-form">
                {searchTab === 'all' ? (
                  <div className="input-group">
                    <span className="input-icon">
                      <SearchIcon width={18} height={18} />
                    </span>
                    <input
                      type="search"
                      placeholder="e.g. Creamy Tuscan Garlic Chicken, Shakshuka, Pasta..."
                      value={query}
                      onChange={(e) => setQuery(e.target.value)}
                      aria-label="Search recipes by name"
                      className="main-input"
                    />
                    {query && (
                      <button
                        type="button"
                        className="btn-clear-input"
                        onClick={() => setQuery('')}
                      >
                        <Close width={14} height={14} />
                      </button>
                    )}
                  </div>
                ) : (
                  <div className="ingredient-finder-group">
                    <div className="input-row">
                      <div className="input-group">
                        <span className="input-icon">🥦</span>
                        <input
                          type="text"
                          placeholder="Type ingredient (e.g. eggs, tomatoes, spinach)"
                          value={ingredientInput}
                          onChange={(e) => setIngredientInput(e.target.value)}
                          onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                              e.preventDefault();
                              addIngredient();
                            }
                          }}
                          className="main-input"
                        />
                      </div>
                      <button
                        type="button"
                        className="btn btn-add"
                        onClick={() => addIngredient()}
                      >
                        <Plus width={16} height={16} /> Add
                      </button>
                    </div>

                    <div className="suggestions-row">
                      <span className="suggestion-label">Quick Add:</span>
                      <div className="suggestion-chips">
                        {POPULAR_INGREDIENTS.filter((item) => !ingredients.includes(item)).map((item) => (
                          <button
                            key={item}
                            type="button"
                            className="suggestion-chip"
                            onClick={() => addIngredient(item)}
                          >
                            + {item}
                          </button>
                        ))}
                      </div>
                    </div>

                    {ingredients.length > 0 && (
                      <div className="chips-wrapper">
                        <div className="chips-header">
                          <span>Ingredients ready ({ingredients.length}):</span>
                          <button
                            type="button"
                            className="btn-text"
                            onClick={() => setIngredients([])}
                          >
                            Clear all
                          </button>
                        </div>
                        <ul className="chips-list">
                          {ingredients.map((name) => (
                            <li key={name} className="active-chip">
                              <span>{name}</span>
                              <button
                                type="button"
                                onClick={() => removeIngredient(name)}
                              >
                                <Close width={12} height={12} />
                              </button>
                            </li>
                          ))}
                        </ul>
                      </div>
                    )}
                  </div>
                )}

                <div className="filters-bar">
                  <div className="select-wrapper">
                    <label>Cuisine</label>
                    <select
                      value={cuisine}
                      onChange={(e) => setCuisine(e.target.value)}
                    >
                      <option value="">All Cuisines</option>
                      {cuisines.map((c) => (
                        <option key={c} value={c}>{c}</option>
                      ))}
                    </select>
                  </div>

                  <div className="select-wrapper">
                    <label>Category</label>
                    <select
                      value={category}
                      onChange={(e) => setCategory(e.target.value)}
                    >
                      <option value="">All Categories</option>
                      {categories.map((c) => (
                        <option key={c} value={c}>{c}</option>
                      ))}
                    </select>
                  </div>

                  <div className="action-buttons">
                    {hasFilters && (
                      <button
                        type="button"
                        className="btn btn-ghost"
                        onClick={clearFilters}
                      >
                        Reset
                      </button>
                    )}
                    <button type="submit" className="btn btn-primary">
                      Find Recipes
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>

          <div className="hero-visual" aria-hidden="true">
            <div className="hero-image-card">
              <img src={heroImage} alt="Fresh delicious meal" loading="eager" />
              <div className="floating-card top-badge">
                <span className="badge-emoji">⚡</span>
                <div>
                  <strong>Quick & Easy</strong>
                  <p>Ready under 30 mins</p>
                </div>
              </div>

              <div className="floating-card bottom-badge">
                <span className="badge-emoji">⭐</span>
                <div>
                  <strong>4.9 Star Favorite</strong>
                  <p>1,240+ Home cooks loved this</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* PERKS STRIP */}
      <section className="perks-strip">
        <div className="container perks-container">
          <div className="perk-item">
            <span className="perk-icon">🥑</span>
            <div>
              <strong>Fresh Ingredients</strong>
              <p>Simple, whole food ideas</p>
            </div>
          </div>
          <div className="perk-item">
            <span className="perk-icon">⏱️</span>
            <div>
              <strong>Time-Saving</strong>
              <p>Filter by quick preparation</p>
            </div>
          </div>
          <div className="perk-item">
            <span className="perk-icon">👩‍🍳</span>
            <div>
              <strong>Tested Recipes</strong>
              <p>Step-by-step foolproof instructions</p>
            </div>
          </div>
        </div>
      </section>

      {/* RECIPES RESULTS SECTION */}
      <section id="recipes" className="container results-section">
        <div className="results-header">
          <div>
            <h2 className="results-title">
              {hasFilters ? 'Matching Recipes' : 'Trending & Popular Recipes'}
            </h2>
            <p className="results-subtitle">
              {hasFilters
                ? 'Showing dishes matching your ingredients and criteria'
                : 'Handpicked favorites from your database'}
            </p>
          </div>

          {!loading && !error && (
            <div className="count-pill">
              <strong>{visible.length}</strong> {visible.length === 1 ? 'recipe' : 'recipes'} found
            </div>
          )}
        </div>

        {error && (
          <div className="status-state state-error" role="alert">
            <div className="state-icon">⚠️</div>
            <h3>Something went wrong</h3>
            <p>{error}</p>
            <button className="btn btn-primary" onClick={clearFilters}>
              Refresh & Try Again
            </button>
          </div>
        )}

        {loading && (
          <div className="recipe-grid">
            {[1, 2, 3, 4, 5, 6].map((i) => (
              <div key={i} className="recipe-skeleton-card">
                <div className="skeleton-image" />
                <div className="skeleton-body">
                  <div className="skeleton-line tag" />
                  <div className="skeleton-line title" />
                  <div className="skeleton-line subtitle" />
                  <div className="skeleton-footer">
                    <div className="skeleton-pill" />
                    <div className="skeleton-pill" />
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}

        {!loading && !error && visible.length === 0 && (
          <div className="status-state state-empty">
            <div className="state-icon">🍳</div>
            <h3>No recipes found</h3>
            <p>Try removing an ingredient or clearing your cuisine filters.</p>
            <button className="btn btn-primary" onClick={clearFilters}>
              Reset All Filters
            </button>
          </div>
        )}

        {!loading && !error && visible.length > 0 && (
          <div className="recipe-grid">
            {visible.map((r) => (
              <RecipeCard
                key={r.id}
                recipe={r}
                bookmarked={savedIds.has(r.id)}
                onBookmark={handleBookmark}
              />
            ))}
          </div>
        )}
      </section>

      {/* =======================================================
          ✨ NEW: GET STARTED IN 3 SIMPLE STEPS (CTA SECTION)
         ======================================================= */}
      <section className="get-started-section">
        <div className="container">
          <div className="get-started-box">
            <div className="get-started-header">
              <span className="badge-tag">Get Started Today</span>
              <h2>How CookMate Works For You</h2>
              <p>No more staring at an empty fridge asking "What should I eat tonight?"</p>
            </div>

            <div className="steps-grid">
              <div className="step-card">
                <div className="step-number">1</div>
                <div className="step-icon">🥦</div>
                <h3>Check Your Pantry</h3>
                <p>Type in whatever ingredients you already have—chicken, cheese, garlic, tomatoes.</p>
              </div>

              <div className="step-card">
                <div className="step-number">2</div>
                <div className="step-icon">⚡</div>
                <h3>Find Instant Matches</h3>
                <p>Our algorithm instantly filters recipes you can cook right away without groceries.</p>
              </div>

              <div className="step-card">
                <div className="step-number">3</div>
                <div className="step-icon">🍽️</div>
                <h3>Cook & Save Favorites</h3>
                <p>Follow step-by-step foolproof instructions and bookmark your best dishes for later.</p>
              </div>
            </div>

            <div className="get-started-action">
              {user ? (
                <a href="#recipes" className="btn btn-primary btn-lg">
                  Explore Your Recipes Now →
                </a>
              ) : (
                <div className="cta-button-group">
                  <Link to="/register" className="btn btn-primary btn-lg">
                    Create Free Account →
                  </Link>
                  <Link to="/login" className="btn btn-ghost btn-lg">
                    Sign In
                  </Link>
                </div>
              )}
            </div>
          </div>
        </div>
      </section>
    </main>
  );
}