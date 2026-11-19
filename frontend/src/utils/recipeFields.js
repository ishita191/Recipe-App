// Base URL for Spring Boot backend
const BACKEND_BASE = 'http://localhost:8080';

// Tolerant readers for backend recipe data
export const unwrap = (res) => (res && res.data !== undefined ? res.data : res);

export const toList = (res) => {
  const d = unwrap(res);
  if (Array.isArray(d)) return d;
  return (d && (d.content || d.recipes || d.items || d.data)) || [];
};

// Extracts image and prefixes backend URL for MySQL stored images
export const getImage = (r) => {
  if (!r) return '';
  const raw =
    r.imageUrl ||
    r.image ||
    r.image_url ||
    r.photoUrl ||
    r.photo ||
    r.thumbnail ||
    r.img ||
    '';

  if (!raw) {
    return 'https://images.unsplash.com/photo-1495521821757-a1efb6729352?auto=format&fit=crop&w=600&q=80';
  }

  // If already full web URL or base64
  if (raw.startsWith('http://') || raw.startsWith('https://') || raw.startsWith('data:image')) {
    return raw;
  }

  // If relative path from Spring Boot (e.g. /images/food.jpg)
  if (raw.startsWith('/')) {
    return `${BACKEND_BASE}${raw}`;
  }

  // If filename stored in MySQL (e.g. food.jpg)
  return `${BACKEND_BASE}/images/${raw}`;
};

export const getPrep = (r) => r?.prepTime ?? r?.preparationTime ?? r?.prepTimeMinutes ?? null;
export const getCook = (r) => r?.cookTime ?? r?.cookingTime ?? r?.cookTimeMinutes ?? null;

export const getTotal = (r) => {
  const p = Number(getPrep(r)) || 0;
  const c = Number(getCook(r)) || 0;
  return p + c || r?.totalTime || 25;
};

export const isSaved = (r) => Boolean(r?.bookmarked ?? r?.isBookmarked ?? r?.saved ?? false);

export const getIngredients = (r) => {
  const list = r?.ingredients || r?.recipeIngredients || [];
  if (typeof list === 'string') {
    return list.split(',').map((item) => ({ name: item.trim(), quantity: '', unit: '' })).filter((i) => i.name);
  }
  return list.map((i) => {
    if (typeof i === 'string') return { name: i };
    return {
      name: i.name || i.ingredient?.name || i.ingredientName || '',
      quantity: i.quantity ?? i.amount ?? '',
      unit: i.unit || '',
    };
  });
};

export const getSteps = (r) => {
  const s = r?.instructions || r?.steps || r?.directions || [];
  if (typeof s === 'string') {
    return s.split(/\r?\n+/).map((x) => x.replace(/^\s*\d+[.)]\s*/, '').trim()).filter(Boolean);
  }
  return s.map((x) => (typeof x === 'string' ? x : x.instruction || x.description || x.text || '')).filter(Boolean);
};

export const minutes = (v) => (v || v === 0 ? `${v} min` : '25 min');