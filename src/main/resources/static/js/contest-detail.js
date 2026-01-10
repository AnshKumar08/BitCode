// js/contest-detail.js

let editor, contestData, currentProblem = 0;
let startTime, timerInterval;

// ✅ Judge0 Public API endpoints
const JUDGE0_SUBMISSIONS_URL = 'https://judge0.com/api/submissions';

const LANGUAGE_CODES = {
    '62': 'java',
    '71': 'python',
    '54': 'cpp'
};

const DEFAULT_CODE = {
    'java': 'public class Main {\n    public static void main(String[] args) {\n        System.out.println("Hello World");\n    }\n}',
    'python': 'print("Hello World")',
    'cpp': '#include <iostream>\nusing namespace std;\n\nint main() {\n    cout << "Hello World";\n    return 0;\n}'
};

document.addEventListener('DOMContentLoaded', async () => {
    // Initialize theme
    initializeTheme();

    // Initialize Ace Editor
    editor = ace.edit('code-editor');
    editor.setTheme('ace/theme/monokai');
    editor.session.setMode('ace/mode/java');
    editor.setValue(DEFAULT_CODE.java);
    editor.setFontSize(14);
    editor.setOptions({
        enableBasicAutocompletion: true,
        enableLiveAutocompletion: true,
        showPrintMargin: false
    });

    // Load contest
    const urlParams = new URLSearchParams(location.search);
    const contestId = urlParams.get('id');

    if (!contestId) {
        alert('No contest selected');
        window.location.href = 'contests.html';
        return;
    }

    try {
        const res = await fetch(`/api/contests/${contestId}`);
        if (!res.ok) throw new Error('Failed to load contest');

        contestData = await res.json();
        document.getElementById('problem-title').textContent = `Contest: ${contestData.title}`;

        // Start contest
        await startContestAPI(contestId);

        // Initialize timer
        startTime = Date.now();
        initializeTimer(contestData.durationMinutes * 60);

        // Load problems
        renderProblems();
        loadProblem(0);

        // Event listeners
        document.getElementById('run-btn').addEventListener('click', runCode);
        document.getElementById('submit-btn').addEventListener('click', submitCode);
        document.getElementById('language-select').addEventListener('change', changeLanguage);

    } catch (e) {
        console.error('Error loading contest:', e);
        displayError('Failed to load contest. Redirecting...');
        setTimeout(() => window.location.href = 'contests.html', 2000);
    }
});

// ✅ Initialize theme system
function initializeTheme() {
    const themeBtn = document.getElementById('theme-toggle-btn');
    const html = document.documentElement;

    // Load saved theme or detect system preference
    const savedTheme = localStorage.getItem('theme') ||
                      (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');

    html.setAttribute('data-theme', savedTheme);
    themeBtn.textContent = savedTheme === 'dark' ? '☀️' : '🌙';

    // Theme toggle
    themeBtn.addEventListener('click', () => {
        const currentTheme = html.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        html.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);
        themeBtn.textContent = newTheme === 'dark' ? '☀️' : '🌙';

        // Update editor theme
        updateEditorTheme(newTheme);
    });

    // Update editor theme on load
    updateEditorTheme(savedTheme);
}

function updateEditorTheme(theme) {
    if (editor) {
        editor.setTheme(theme === 'dark' ? 'ace/theme/monokai' : 'ace/theme/chrome');
    }
}

async function startContestAPI(id) {
    try {
        await fetch(`/api/contests/${id}/start`, { method: 'POST' });
    } catch (e) {
        console.error('Error starting contest:', e);
    }
}

function renderProblems() {
    const nav = document.querySelector('.split-view');
    const problemNav = document.createElement('div');
    problemNav.style.cssText = `
        display: flex;
        gap: 0.5rem;
        margin-bottom: 1rem;
        flex-wrap: wrap;
        padding: 1rem;
        background: var(--bg-secondary);
        border-radius: 12px;
    `;

    problemNav.innerHTML = contestData.problems.map((p, i) => `
        <button style="
            padding: 0.6rem 1rem;
            background: ${i === 0 ? 'var(--accent-color)' : 'var(--bg-primary)'};
            color: ${i === 0 ? 'white' : 'var(--text-primary)'};
            border: 1px solid var(--border-color);
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s;
        " onclick="loadProblem(${i})">
            ${i + 1}. ${p.title.substring(0, 15)}${p.title.length > 15 ? '...' : ''}
        </button>
    `).join('');

    nav.insertBefore(problemNav, nav.firstChild);
}

async function loadProblem(index) {
    currentProblem = index;
    const problem = contestData.problems[index];

    document.getElementById('problem-title').textContent = problem.title;
    document.getElementById('problem-difficulty-badge').textContent = problem.difficulty;
    document.getElementById('problem-difficulty-badge').className =
        `difficulty-tag difficulty-${problem.difficulty.toLowerCase()}`;

    document.getElementById('problem-body').innerHTML =
        problem.description || `<h2>Solve: ${problem.title}</h2><p>Difficulty: ${problem.difficulty}</p><p>Write your solution in the editor and click Submit to test your code.</p>`;

    // Load problem-specific code (from localStorage or default)
    const savedCode = localStorage.getItem(`contest_${contestData.id}_problem_${problem.id}`);
    editor.setValue(savedCode || DEFAULT_CODE.java);

    // Save code on change
    editor.session.off('change');
    editor.session.on('change', () => {
        localStorage.setItem(`contest_${contestData.id}_problem_${problem.id}`, editor.getValue());
    });
}

function changeLanguage() {
    const langId = document.getElementById('language-select').value;
    const lang = LANGUAGE_CODES[langId];

    editor.session.setMode(`ace/mode/${lang}`);
    const savedCode = localStorage.getItem(`contest_${contestData.id}_problem_${currentProblem}_${lang}`);
    editor.setValue(savedCode || DEFAULT_CODE[lang]);
}

// ✅ RUN CODE - Fixed CORS issue
// ✅ RUN CODE - Fixed CORS issue
async function runCode() {
    const code = editor.getValue();
    const langId = document.getElementById('language-select').value;

    if (!code.trim()) {
        displayError('Code is empty');
        return;
    }

    displayLoading('⏳ Running your code...');
    document.getElementById('run-btn').disabled = true;

    try {
        // ✅ Use Backend Proxy instead of direct Judge0 call
        const submitRes = await fetch('/api/execute', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                problemId: contestData.problems[currentProblem].id,
                language: langId,
                code: code
            })
        });

        if (!submitRes.ok) {
            throw new Error('Failed to execute code');
        }

        const result = await submitRes.json();
        displayOutput(result);

    } catch (e) {
        console.error('Execution error:', e);
        displayError(`Error: ${e.message}`);
    } finally {
        document.getElementById('run-btn').disabled = false;
    }
}



// ✅ SUBMIT CODE
// ✅ SUBMIT CODE
async function submitCode() {
    const code = editor.getValue();
    const problem = contestData.problems[currentProblem];

    if (!code.trim()) {
        alert('Code cannot be empty');
        return;
    }

    displayLoading('⏳ Submitting solution...');
    document.getElementById('submit-btn').disabled = true;

    try {
        // Execute via backend
        const submitRes = await fetch('/api/execute', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                problemId: problem.id,
                language: document.getElementById('language-select').value,
                code: code
            })
        });

        const result = await submitRes.json();
        displayOutput(result);

        // Check if accepted
        if (result.status === 'Accepted') {
            // Accepted - submit to backend for scoring
            try {
                const res = await fetch(`/api/contests/${contestData.id}/submit`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        problemId: problem.id,
                        code: code
                    })
                });

                if (res.ok) {
                    alert('✅ Solution submitted! +10 points');
                    // Load next problem or finish
                    if (currentProblem < contestData.problems.length - 1) {
                        loadProblem(currentProblem + 1);
                    } else {
                        finishContest();
                    }
                }
            } catch (e) {
                console.error('Submit to backend error:', e);
            }
        } else {
            alert('Please fix the errors and try again');
        }
    } catch (e) {
        console.error('Submit error:', e);
        displayError(`Error: ${e.message}`);
    } finally {
        document.getElementById('submit-btn').disabled = false;
    }
}


function displayOutput(result) {
    const output = document.getElementById('output-console');

    if (!result.status) {
        displayError('No status returned');
        return;
    }

    // ✅ CHANGED: Use string status instead of status.id
    if (result.status === 'Accepted') {
        output.innerHTML = `
            <div class="verdict-container">
                <div class="verdict-badge verdict-accepted">
                    <span class="status-icon">✅</span> ACCEPTED
                </div>
                <div class="output-panel">
                    <div class="output-header"><span class="status-icon">📤</span> Output</div>
                    <div class="output-content">${escapeHtml(result.output || 'No output')}</div>
                </div>
            </div>
        `;
    } else if (result.status === 'Wrong Answer') {
        output.innerHTML = `
            <div class="verdict-container">
                <div class="verdict-badge verdict-wrong">
                    <span class="status-icon">❌</span> WRONG ANSWER
                </div>
                <div class="output-panel">
                    <div class="output-header"><span class="status-icon">📤</span> Your Output</div>
                    <div class="output-content">${escapeHtml(result.output || 'N/A')}</div>
                </div>
                <div class="output-panel" style="margin-top: 1rem;">
                    <div class="output-header"><span class="status-icon">✅</span> Expected Output</div>
                    <div class="output-content">${escapeHtml(result.expectedOutput || 'N/A')}</div>
                </div>
            </div>
        `;
    } else if (result.status === 'Compilation Error') {
        output.innerHTML = `
            <div class="verdict-container">
                <div class="verdict-badge verdict-error">
                    <span class="status-icon">⚠️</span> COMPILATION ERROR
                </div>
                <div class="output-panel">
                    <div class="output-header"><span class="status-icon">🔧</span> Error</div>
                    <div class="output-content">${escapeHtml(result.error || 'Unknown error')}</div>
                </div>
            </div>
        `;
    } else if (result.status === 'Runtime Error') {
        output.innerHTML = `
            <div class="verdict-container">
                <div class="verdict-badge verdict-error">
                    <span class="status-icon">💥</span> RUNTIME ERROR
                </div>
                <div class="output-panel">
                    <div class="output-header"><span class="status-icon">🔧</span> Error</div>
                    <div class="output-content">${escapeHtml(result.error || 'Unknown error')}</div>
                </div>
            </div>
        `;
    } else if (result.status === 'Error') {
        displayError(result.error || 'Unknown error');
    }
}


function displayLoading(message) {
    document.getElementById('output-console').innerHTML = `
        <div style="text-align: center;">
            <div style="font-size: 2rem; margin-bottom: 1rem; animation: spin 1s linear infinite;">⏳</div>
            <p>${message}</p>
        </div>
    `;
}

function displayError(message) {
    document.getElementById('output-console').innerHTML = `
        <div class="verdict-container">
            <div class="verdict-badge verdict-error">
                <span class="status-icon">❌</span> ERROR
            </div>
            <div class="output-panel">
                <div class="output-header"><span class="status-icon">⚠️</span> Message</div>
                <div class="output-content">${escapeHtml(message)}</div>
            </div>
        </div>
    `;
}

function decodeBase64(str) {
    try {
        return atob(str);
    } catch (e) {
        return str;
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function initializeTimer(seconds) {
    let remaining = seconds;

    const updateTimer = () => {
        const hours = Math.floor(remaining / 3600);
        const minutes = Math.floor((remaining % 3600) / 60);
        const secs = remaining % 60;

        const display = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;

        if (remaining <= 0) {
            clearInterval(timerInterval);
            finishContest();
        }

        remaining--;
    };

    updateTimer();
    timerInterval = setInterval(updateTimer, 1000);
}

async function finishContest() {
    clearInterval(timerInterval);

    try {
        const res = await fetch(`/api/contests/${contestData.id}/complete`, { method: 'POST' });
        const result = await res.json();

        alert(`🎉 Contest Completed!\n\nScore: ${result.score || '0'}\n\nProblems Solved: ${Math.floor((result.score || 0) / 10)}/${contestData.problems.length}`);

        setTimeout(() => {
            window.location.href = 'contests.html';
        }, 2000);
    } catch (e) {
        console.error('Error completing contest:', e);
    }
}
