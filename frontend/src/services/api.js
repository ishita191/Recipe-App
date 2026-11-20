import axios from 'axios';

// Pointing to your local Spring Boot backend
const API_BASE = 'https://recipe-app-vtzp.onrender.com/api';
const api = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Attach JWT token to requests if user is signed in
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Auto-handle 401 Unauthorized (expired token)
// api.interceptors.response.use(
//   (res) => res,
//   (err) => {
//     if (err.response?.status === 401) {
//       localStorage.removeItem('token');
//       localStorage.removeItem('user');
//       window.location.href = '/login';
//     }
//     return Promise.reject(err);
//   }
// );
// Attach JWT token ONLY if a real token exists
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token && token !== 'null' && token !== 'undefined' && token.trim() !== '') {
    config.headers.Authorization = `Bearer ${token}`;
  } else {
    delete config.headers.Authorization; // Prevents unnecessary preflight on public requests
  }
  return config;
});

const MEAL_DB_URL = 'https://www.themealdb.com/api/json/v1/1';

function mapExternalMeal(meal) {
  if (!meal) return null;

  const ingredients = [];
  for (let i = 1; i <= 20; i++) {
    const name = meal[`strIngredient${i}`]?.trim();
    const quantity = meal[`strMeasure${i}`]?.trim();
    if (name) {
      ingredients.push({ name, quantity: quantity || '' });
    }
  }

  const steps = (meal.strInstructions || '')
    .split(/\r?\n|(?<=\.)\s+/)
    .map((step) => step.trim())
    .filter(Boolean);

  return {
    id: `ext-${meal.idMeal}`,
    name: meal.strMeal,
    title: meal.strMeal,
    imageUrl: meal.strMealThumb,
    image: meal.strMealThumb,
    category: meal.strCategory || '',
    cuisine: meal.strArea || '',
    description: meal.strInstructions?.slice(0, 140) + '...',
    ingredients,
    steps,
    instructions: meal.strInstructions || '',
    servings: '2-4',
    difficulty: 'Medium',
    prepTime: '20 mins',
    cookTime: '25 mins',
    external: true,
  };
}

async function searchExternalRecipes(query) {
  try {
    const response = await axios.get(`${MEAL_DB_URL}/search.php`, {
      params: { s: query },
    });
    return (response.data.meals || []).map(mapExternalMeal).filter(Boolean);
  } catch (e) {
    return [];
  }
}

async function getExternalRecipe(id) {
  const mealId = id.replace('ext-', '');
  const response = await axios.get(`${MEAL_DB_URL}/lookup.php`, {
    params: { i: mealId },
  });
  return mapExternalMeal(response.data.meals?.[0]);
}

/* =======================================================
   AUTH SERVICE (Connects to Spring Boot Auth)
   ======================================================= */
export const authService = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
  getCurrentUser: () => api.get('/auth/me'),
};

/* =======================================================
   RECIPE SERVICE (Connects to http://localhost:8080/api/recipe)
   ======================================================= */
export const recipeService = {
  // 1. Fetch all 24 recipes from your Spring Boot database
  getAll: () => api.get('/recipes'),

  // 2. Fetch single recipe by ID
  getById: async (id) => {
    if (String(id).startsWith('ext-')) {
      const recipe = await getExternalRecipe(String(id));
      return { data: recipe };
    }
    return api.get(`/recipes/${id}`);
  },

  // 3. Search recipes (searches local 24 recipes, then falls back to MealDB)
  search: async (query) => {
    try {
      const response = await api.get('/recipe/search', {
        params: { query },
      });
      const data = response.data;
      const localRecipes = Array.isArray(data)
        ? data
        : data?.recipes || data?.content || [];

      if (localRecipes.length > 0) {
        return response;
      }
    } catch (err) {
      console.warn('Local search not matched, falling back to MealDB...');
    }

    const externalRecipes = await searchExternalRecipes(query);
    return { data: externalRecipes };
  },

  // 4. Search by ingredients (Fridge Finder)
  searchByIngredients: (ingredients) => {
    const value = Array.isArray(ingredients) ? ingredients.join(',') : ingredients;
    return api.get('/recipe/search/ingredients', {
      params: { ingredients: value },
    });
  },

  toggleBookmark: (id) => api.post(`/recipes/${id}/bookmark`),
  // toggleBookmark: (id) => api.post(`/recipe/${id}/bookmark`),

  // 6. Get saved/bookmarked recipes
  getBookmarks: () => api.get('/recipe/saved'),
  getSaved: () => api.get('/recipe/saved'),
};

export const ingredientService = {
  getAll: () => api.get('/ingredients'),
  search: (q) => api.get(`/ingredients/search?query=${q}`),
};

export default api;