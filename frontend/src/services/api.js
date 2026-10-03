import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

const api = axios.create({ baseURL: API_BASE });

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
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
    imageUrl: meal.strMealThumb,
    category: meal.strCategory || '',
    cuisine: meal.strArea || '',
    description: '',
    ingredients,
    steps,
    instructions: meal.strInstructions || '',
    servings: '',
    difficulty: '',
    prepTime: '',
    cookTime: '',
    external: true,
  };
}

async function searchExternalRecipes(query) {
  const response = await axios.get(`${MEAL_DB_URL}/search.php`, {
    params: { s: query },
  });

  return (response.data.meals || [])
    .map(mapExternalMeal)
    .filter(Boolean);
}

async function getExternalRecipe(id) {
  const mealId = id.replace('ext-', '');

  const response = await axios.get(`${MEAL_DB_URL}/lookup.php`, {
    params: { i: mealId },
  });

  return mapExternalMeal(response.data.meals?.[0]);
}
api.interceptors.response.use(
  res => res,
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

export const authService = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
};

export const recipeService = {
  getAll: () => api.get('/recipes'),
  getById: async (id) => {
  if (String(id).startsWith('ext-')) {
    const recipe = await getExternalRecipe(String(id));
    return { data: recipe };
  }

  return api.get(`/recipes/${id}`);
},

search: async (query) => {
  const response = await api.get('/recipes/search', {
    params: { query },
  });

  const data = response.data;
  const localRecipes = Array.isArray(data)
    ? data
    : data?.recipes || data?.content || [];

  if (localRecipes.length > 0) {
    return response;
  }

  const externalRecipes = await searchExternalRecipes(query);
  return { data: externalRecipes };
},
  searchByIngredients: (ingredients) =>
    api.get(`/recipes/search/ingredients?ingredients=${ingredients.join(',')}`),
  toggleBookmark: (id) => api.post(`/recipes/${id}/bookmark`),
  getBookmarks: () => api.get('/recipes/bookmarks'),
};

export const ingredientService = {
  getAll: () => api.get('/ingredients'),
  search: (q) => api.get(`/ingredients/search?query=${q}`),
};

export default api;
