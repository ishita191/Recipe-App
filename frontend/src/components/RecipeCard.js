import React from 'react';
import { Link } from 'react-router-dom';
import { Heart, Clock, Users, Gauge } from './Icons';
import { getImage, getTotal, minutes, isSaved } from '../utils/recipeFields';


export default function RecipeCard({ recipe, onBookmark, bookmarked }) {
  if (!recipe) return null;
  const saved = bookmarked ?? isSaved(recipe);
  const img = getImage(recipe);
  const total = minutes(getTotal(recipe));

  return (
    <article className="recipe-card">
      <div className="card-media">
        <Link to={`/recipe/${recipe.id}`} tabIndex={-1} aria-hidden="true">
          {img ? <img src={recipe.imageUrl} alt={recipe.name} loading="lazy" /> : <div className="img-fallback"/>}    
        </Link>
        {onBookmark && (
          <button
            className={`bookmark-btn ${saved ? 'is-saved' : ''}`}
            aria-label={saved ? 'Remove from saved recipes' : 'Save recipe'}
            aria-pressed={saved}
            onClick={() => onBookmark(recipe)}
          >
            <Heart filled={saved} />
          </button>
        )}
        {recipe.category && <span className="tag">{recipe.category}</span>}
      </div>

      <Link to={`/recipe/${recipe.id}`} className="card-body">
        <h3>{recipe.name || recipe.title}</h3>
        {recipe.description && <p className="card-desc">{recipe.description}</p>}
        <ul className="meta">
          {total && <li><Clock width={15} height={15} />{total}</li>}
          {recipe.servings && <li><Users width={15} height={15} />{recipe.servings} servings</li>}
          {recipe.difficulty && <li><Gauge width={15} height={15} />{recipe.difficulty}</li>}
        </ul>
        {recipe.cuisine && <span className="cuisine">{recipe.cuisine}</span>}
      </Link>
    </article>
  );
}