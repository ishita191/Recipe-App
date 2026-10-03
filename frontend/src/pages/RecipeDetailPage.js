import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { recipeService } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { Back, Clock, Users, Gauge, Heart } from '../components/Icons';
import {
  unwrap, getImage, getPrep, getCook, getIngredients, getSteps, minutes, isSaved,
} from '../utils/recipeFields';

export default function RecipeDetailPage() {
  const { id } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [recipe, setRecipe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [saved, setSaved] = useState(false);

  useEffect(() => {
    let active = true;
    setLoading(true);
    setError('');
    recipeService.getById(id) // API
      .then((res) => {
        if (!active) return;
        const data = unwrap(res);
        setRecipe(data);
        setSaved(isSaved(data));
      })
      .catch(() => active && setError('We could not load this recipe. It may have been removed.'))
      .finally(() => active && setLoading(false));
    return () => { active = false; };
  }, [id]);

  const handleSave = async () => {
    if (!user) return navigate('/login');
    try {
      await recipeService.toggleBookmark(id); // API
      setSaved((s) => !s);
    } catch (e) {
      setError('Could not update your saved recipes. Please try again.');
    }
  };

  if (loading) return <main className="container detail"><div className="skeleton-card tall" aria-busy="true" /></main>;

  if (!recipe) {
    return (
      <main className="container detail">
        <div className="state state-error" role="alert">
          <h3>Recipe unavailable</h3>
          <p>{error}</p>
          <Link to="/" className="btn btn-primary">Back to recipes</Link>
        </div>
      </main>
    );
  }

  const img = getImage(recipe);
  const ingredients = getIngredients(recipe);
  const steps = getSteps(recipe);
  const facts = [
    { icon: <Clock width={18} height={18} />, label: 'Prep', value: minutes(getPrep(recipe)) },
    { icon: <Clock width={18} height={18} />, label: 'Cook', value: minutes(getCook(recipe)) },
    { icon: <Users width={18} height={18} />, label: 'Servings', value: recipe.servings },
    { icon: <Gauge width={18} height={18} />, label: 'Difficulty', value: recipe.difficulty },
  ].filter((f) => f.value);
  const nutrition = recipe.nutrition || recipe.nutritionInfo;

  return (
    <main className="container detail">
      <Link to="/" className="back-link"><Back width={18} height={18} /> Back to recipes</Link>
      {error && <div className="state state-error" role="alert"><p>{error}</p></div>}

      <div className="detail-grid">
        <div className="detail-media">{img ? <img src={img} alt={recipe.name || recipe.title} /> : <div className="img-fallback" />}</div>

        <div className="detail-head">
          <div className="tags">
            {recipe.cuisine && <span className="pill">{recipe.cuisine}</span>}
            {recipe.category && <span className="pill pill-soft">{recipe.category}</span>}
          </div>
          <h1>{recipe.name || recipe.title}</h1>
          {recipe.description && <p className="lead">{recipe.description}</p>}

          {facts.length > 0 && (
            <dl className="facts">
              {facts.map((f) => (
                <div key={f.label}><dt>{f.icon}{f.label}</dt><dd>{f.value}</dd></div>
              ))}
            </dl>
          )}

          <button className={`btn btn-lg ${saved ? 'btn-soft' : 'btn-primary'}`} onClick={handleSave} aria-pressed={saved}>
            <Heart filled={saved} width={18} height={18} /> {saved ? 'Saved' : 'Save Recipe'}
          </button>
        </div>
      </div>

      <div className="detail-body">
        {ingredients.length > 0 && (
          <section className="panel">
            <h2>Ingredients</h2>
            <ul className="ingredient-list">
              {ingredients.map((i, idx) => (
                <li key={`${i.name}-${idx}`}>
                  <span>{i.name}</span>
                  <span className="qty">{[i.quantity, i.unit].filter(Boolean).join(' ')}</span>
                </li>
              ))}
            </ul>
          </section>
        )}

        {steps.length > 0 && (
          <section className="panel">
            <h2>Instructions</h2>
            <ol className="steps">
              {steps.map((s, idx) => <li key={idx}>{s}</li>)}
            </ol>
          </section>
        )}

        {nutrition && typeof nutrition === 'object' && Object.keys(nutrition).length > 0 && (
          <section className="panel">
            <h2>Nutrition</h2>
            <dl className="nutrition">
              {Object.entries(nutrition).filter(([, v]) => v !== null && v !== '').map(([k, v]) => (
                <div key={k}><dt>{k.replace(/([A-Z])/g, ' $1')}</dt><dd>{String(v)}</dd></div>
              ))}
            </dl>
          </section>
        )}
      </div>
    </main>
  );
}