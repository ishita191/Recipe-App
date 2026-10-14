// Tolerant readers for backend recipe data. If your field names differ, add them here (one place only).
export const unwrap = (res) => (res && res.data !== undefined ? res.data : res);

export const toList = (res) => {
  const d = unwrap(res);
  if (Array.isArray(d)) return d;
  return (d && (d.content || d.recipes || d.items)) || [];
};

export const getImage = (r) => (r && (r.imageUrl || r.image || r.imageURL || r.photoUrl || r.thumbnail)) || '';
export const getPrep = (r) => r?.prepTime ?? r?.preparationTime ?? r?.prepTimeMinutes ?? null;
export const getCook = (r) => r?.cookTime ?? r?.cookingTime ?? r?.cookTimeMinutes ?? null;
export const getTotal = (r) => {
  const p = Number(getPrep(r)) || 0;
  const c = Number(getCook(r)) || 0;
  return p + c || null;
};
export const isSaved = (r) => Boolean(r?.bookmarked ?? r?.isBookmarked ?? r?.saved ?? false);

export const getIngredients = (r) => {
  const list = r?.ingredients || r?.recipeIngredients || [];
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

export const minutes = (v) => (v || v === 0 ? `${v} min` : null);