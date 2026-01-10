// js/auth-guard.js - COMPLETE WORKING VERSION

const PROTECTED_PAGES = [
    'problem.html',
    'study-groups.html',
    'contests.html',
    'discuss.html',
    'leaderboard.html',
    'explore.html'
];

function getCurrentPage() {
    return window.location.pathname.split('/').pop() || 'index.html';
}

function isProtectedPage() {
    const currentPage = getCurrentPage();
    return PROTECTED_PAGES.includes(currentPage);
}

async function isLoggedIn() {
    const token = localStorage.getItem('token');
    if (!token) return false;

    try {
        const response = await fetch('/api/auth/validate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        const isValid = response.ok;

        // Clear invalid tokens
        if (!isValid) {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
        }

        return isValid;
    } catch (error) {
        console.error('Token validation error:', error);
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        return false;
    }
}

async function protectPage() {
    if (isProtectedPage()) {
        const loggedIn = await isLoggedIn();
        if (!loggedIn) {
            window.location.href = 'login.html';
            return false;
        }
    }
    return true;
}

async function getUserInfo() {
    const token = localStorage.getItem('token');
    if (!token) return null;

    try {
        const response = await fetch('/api/auth/validate', {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            return await response.json();
        }
        return null;
    } catch {
        return null;
    }
}

// Auto-protect on page load
document.addEventListener('DOMContentLoaded', async () => {
    await protectPage();
});

// Global access
window.AuthGuard = {
    isLoggedIn,
    protectPage,
    getUserInfo,
    getCurrentPage,
    isProtectedPage
};
