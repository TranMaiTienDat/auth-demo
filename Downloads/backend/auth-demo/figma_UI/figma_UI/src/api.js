// src/api.js
const API = import.meta.env.VITE_API_URL;

// Lưu token ở bộ nhớ JS (an toàn hơn localStorage). Nếu muốn nhớ sau khi reload trang, dùng localStorage.
let accessToken = null;

export function setAccessToken(token) {
  accessToken = token;
  // Nếu muốn giữ sau khi F5: localStorage.setItem("token", token);
}
export function getAccessToken() {
  // Nếu dùng localStorage: return localStorage.getItem("token");
  return accessToken;
}
export function clearAccessToken() {
  accessToken = null;
  // Nếu dùng localStorage: localStorage.removeItem("token");
}

// Hàm gọi fetch kèm token (nếu có)
export async function apiFetch(path, options = {}) {
  const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
  const token = getAccessToken();
  if (token) headers.Authorization = `Bearer ${token}`;

  const res = await fetch(`${API}${path}`, {
    ...options,
    headers
  });

  // Ở bản đơn giản này, nếu 401 thì ném lỗi (chưa có refresh token)
  if (!res.ok) {
    let msg = "Request failed";
    try {
      const err = await res.json();
      msg = err.error || msg;
    } catch {}
    throw new Error(`${res.status}: ${msg}`);
  }

  return res;
}

// API cụ thể:
export async function register(username, password) {
  const res = await apiFetch("/auth/register", {
    method: "POST",
    body: JSON.stringify({ username, password })
  });
  return res.json();
}

export async function login(username, password) {
  const res = await apiFetch("/auth/login", {
    method: "POST",
    body: JSON.stringify({ username, password })
  });
  const data = await res.json();
  if (data.token) setAccessToken(data.token);
  return data;
}

export async function getProfile() {
  const res = await apiFetch("/profile", { method: "GET" });
  return res.json();
}

export async function logout() {
  clearAccessToken();
}
