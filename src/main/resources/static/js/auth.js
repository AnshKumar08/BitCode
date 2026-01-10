// js/auth.js - REMOVE isLoggedIn() function (use AuthGuard instead)

const AUTH_TOKEN = 'token';
const AUTH_USERNAME = 'username';
const AUTH_USER_ID = 'userId';
const TOKEN_REFRESH_INTERVAL = 6 * 24 * 60 * 60 * 1000;

function getToken() { return localStorage.getItem(AUTH_TOKEN); }
function getUsername() { return localStorage.getItem(AUTH_USERNAME) || 'Guest'; }
function getUserId() { return localStorage.getItem(AUTH_USER_ID); }

function logout() {
    localStorage.removeItem(AUTH_TOKEN);
    localStorage.removeItem(AUTH_USERNAME);
    localStorage.removeItem(AUTH_USER_ID);
    localStorage.removeItem('tokenRefreshTimer');
    window.location.href = 'login.html';
}

// ✅ USE AuthGuard.isLoggedIn() instead of local isLoggedIn()
async function authFetch(url, options = {}) {
    const token = getToken();
    if (!options.headers) options.headers = {};
    if (token) options.headers['Authorization'] = `Bearer ${token}`;

    const response = await fetch(url, options);
    if (response.status === 401) logout();
    return response;
}

async function refreshToken() {
    const token = getToken();
    if (!token) return false;

    try {
        const response = await fetch('/api/auth/refresh', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) return false;

        const data = await response.json();
        localStorage.setItem(AUTH_TOKEN, data.token);
        localStorage.setItem(AUTH_USERNAME, data.username);
        localStorage.setItem(AUTH_USER_ID, data.userId);
        setupTokenRefresh();
        return true;
    } catch {
        logout();
        return false;
    }
}

function setupTokenRefresh() {
    const existing = localStorage.getItem('tokenRefreshTimer');
    if (existing) clearTimeout(parseInt(existing));

    const timer = setTimeout(refreshToken, TOKEN_REFRESH_INTERVAL);
    localStorage.setItem('tokenRefreshTimer', timer.toString());
}

// ✅ FIXED: Use AuthGuard.isLoggedIn()
async function validateToken() {
    if (!(await window.AuthGuard?.isLoggedIn?.())) return false;

    try {
        const response = await fetch('/api/auth/validate', {
            headers: { 'Authorization': `Bearer ${getToken()}` }
        });
        if (!response.ok) return await refreshToken();

        const data = await response.json();
        console.log(`✅ Token valid: ${data.expiresIn}s`);
        setupTokenRefresh();
        return true;
    } catch {
        logout();
        return false;
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    if (getToken()) await validateToken();
});
