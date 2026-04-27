/* =====================================================
   EcommercePlatform — Shared JS Utilities
   ===================================================== */

const API_BASE = '';   // same origin

/* ---- Token helpers ---- */
function saveToken(token) { sessionStorage.setItem('ep_token', token); }
function getToken()       { return sessionStorage.getItem('ep_token'); }
function clearToken()     { sessionStorage.removeItem('ep_token'); }

/** Decode JWT payload (no verification — UI only) */
function decodeJwt(token) {
  try {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload.replace(/-/g,'+').replace(/_/g,'/')));
  } catch { return null; }
}

function getRole() {
  const t = getToken();
  if (!t) return null;
  const p = decodeJwt(t);
  // scope is space-separated, e.g. "ROLE_VENDOR"
  const scope = p?.scope || '';
  const parts = scope.split(' ');
  for (const part of parts) {
    const r = part.replace('ROLE_', '').toLowerCase();
    if (['admin','vendor','customer'].includes(r)) return r;
  }
  return null;
}

function getUsername() {
  const t = getToken();
  if (!t) return null;
  return decodeJwt(t)?.sub || null;
}

/* ---- Toast notifications ---- */
function showToast(message, type = 'success') {
  const existing = document.querySelector('.toast');
  if (existing) existing.remove();

  const icon = type === 'success' ? '✓' : '✕';
  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.innerHTML = `<span>${icon}</span><span>${message}</span>`;
  document.body.appendChild(toast);

  setTimeout(() => {
    toast.style.animation = 'toastIn 0.3s ease reverse both';
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}

/* ---- API helpers ---- */
async function apiPost(path, body) {
  const res = await fetch(API_BASE + path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });
  const text = await res.text();
  let data;
  try { data = JSON.parse(text); } catch { data = text; }
  return { ok: res.ok, status: res.status, data };
}

async function apiPut(path, body, useToken = false) {
  const headers = { 'Content-Type': 'application/json' };
  if (useToken) headers['Authorization'] = `Bearer ${getToken()}`;
  const res = await fetch(API_BASE + path, {
    method: 'PUT',
    headers,
    body: body !== undefined ? JSON.stringify(body) : undefined
  });
  const text = await res.text();
  let data;
  try { data = JSON.parse(text); } catch { data = text; }
  return { ok: res.ok, status: res.status, data };
}

async function apiPutParams(path, params, useToken = false) {
  const headers = {};
  if (useToken) headers['Authorization'] = `Bearer ${getToken()}`;
  const url = API_BASE + path + '?' + new URLSearchParams(params).toString();
  const res = await fetch(url, { method: 'PUT', headers });
  const text = await res.text();
  let data;
  try { data = JSON.parse(text); } catch { data = text; }
  return { ok: res.ok, status: res.status, data };
}

/* ---- Set button loading state ---- */
function setLoading(btn, loading, label = btn.dataset.label) {
  if (loading) {
    btn.dataset.label = btn.innerHTML;
    btn.innerHTML = `<span class="spinner"></span>`;
    btn.disabled = true;
  } else {
    btn.innerHTML = btn.dataset.label || label;
    btn.disabled = false;
  }
}

/* ---- Redirect if not logged in ---- */
function requireAuth(expectedRole) {
  const token = getToken();
  if (!token) { window.location.href = '/login'; return false; }
  const decoded = decodeJwt(token);
  if (!decoded || Date.now() / 1000 > decoded.exp) {
    clearToken(); window.location.href = '/login'; return false;
  }
  const role = getRole();
  if (expectedRole && role !== expectedRole) {
    window.location.href = '/dashboard/' + (role || '');
    return false;
  }
  return true;
}

/* ---- Logout ---- */
function logout() {
  clearToken();
  window.location.href = '/login';
}
