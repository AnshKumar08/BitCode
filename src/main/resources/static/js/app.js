// --- THEME SWITCHER LOGIC ---
const themeToggleBtn = document.getElementById("theme-toggle-btn");
const body = document.body;

/**
 * Applies the theme from localStorage when the page loads.
 */
const applySavedTheme = () => {
  const savedTheme = localStorage.getItem("theme");
  // Default to light theme if no preference is saved
  if (savedTheme === "dark") {
    body.classList.add("dark-mode");
    themeToggleBtn.textContent = "☀️";
  } else {
    body.classList.remove("dark-mode");
    themeToggleBtn.textContent = "🌙";
  }
};

/**
 * Handles the click event to toggle the theme.
 */
themeToggleBtn.addEventListener("click", () => {
  body.classList.toggle("dark-mode");

  // Save the user's preference to localStorage
  if (body.classList.contains("dark-mode")) {
    localStorage.setItem("theme", "dark");
    themeToggleBtn.textContent = "☀️";
  } else {
    localStorage.setItem("theme", "light");
    themeToggleBtn.textContent = "🌙";
  }
});

/**
 * Main script execution starts after the page is fully loaded.
 */
document.addEventListener("DOMContentLoaded", () => {
  // Apply the correct theme as soon as the page loads
  applySavedTheme();

//  // --- STUDY GROUP PAGE LOGIC ---
//  // This code only runs if it finds the 'groups-list' element,
//  // meaning we are on the Study Groups page (index.html).
//  const groupsList = document.getElementById("groups-list");
//  if (groupsList) {
//    const createGroupForm = document.getElementById("create-group-form");
//    const showFormBtn = document.getElementById("show-create-form-btn");
//    const cancelBtn = document.getElementById("cancel-create-btn");
//    const API_URL = "/api/study-groups"; // Your backend endpoint
//
//    /**
//     * Fetches study groups from the backend and displays them.
//     */
//    const fetchAndDisplayGroups = async () => {
//      try {
//        const response = await fetch(API_URL);
//        if (!response.ok) {
//          throw new Error("Network response was not ok");
//        }
//        const groups = await response.json(); // Now using real data
//
//        groupsList.innerHTML = ""; // Clear existing list
//
//        if (groups.length === 0) {
//          groupsList.innerHTML =
//            '<p class="placeholder-content">No study groups found. Why not create one?</p>';
//          return;
//        }
//
//        groups.forEach((group) => {
//          const card = document.createElement("div");
//          card.className = "group-card";
//          card.innerHTML = `
//                        <h3>${group.name}</h3>
//                        <p>${group.description}</p>
//                        <button onclick="joinGroup(${group.id})">Join Group</button>
//                    `;
//          groupsList.appendChild(card);
//        });
//      } catch (error) {
//        console.error("Failed to fetch groups:", error);
//        groupsList.innerHTML =
//          '<p class="placeholder-content">Error loading study groups. Please try again later.</p>';
//      }
//    };
//
//    /**
//     * Handles the submission of the "Create Group" form.
//     */
//    createGroupForm.addEventListener("submit", async (event) => {
//      event.preventDefault(); // Stop form reload
//
//      const nameInput = document.getElementById("group-name");
//      const descriptionInput = document.getElementById("group-description");
//
//      const newGroup = {
//        name: nameInput.value,
//        description: descriptionInput.value,
//      };
//
//      try {
//        const response = await fetch(API_URL, {
//          method: "POST",
//          headers: { "Content-Type": "application/json" },
//          body: JSON.stringify(newGroup),
//        });
//
//        if (response.ok) {
//          createGroupForm.reset();
//          createGroupForm.classList.add("hidden");
//          showFormBtn.style.display = "block";
//          fetchAndDisplayGroups(); // Refresh the list
//        } else {
//          alert("Failed to create group. Please check your input.");
//        }
//      } catch (error) {
//        console.error("Error creating group:", error);
//        alert("An error occurred. Please try again.");
//      }
//    });
//
//    // Event listeners for showing/hiding the form
//    showFormBtn.addEventListener("click", () => {
//      createGroupForm.classList.remove("hidden");
//      showFormBtn.style.display = "none";
//    });
//
//    cancelBtn.addEventListener("click", () => {
//      createGroupForm.classList.add("hidden");
//      showFormBtn.style.display = "block";
//      createGroupForm.reset();
//    });
//
//    // Dummy function for joining a group
//    window.joinGroup = (groupId) => {
//      alert(
//        `Joining group with ID: ${groupId}. (Functionality to be implemented)`
//      );
//    };
//
//    // Initial load for the study groups page
//    fetchAndDisplayGroups();
//  }

  // --- DISCUSS PAGE LOGIC ---
  // This code only runs if it finds the 'discuss-list-container'
  const discussList = document.getElementById("discuss-list-container");
  if (discussList) {
    const API_URL = "/api/posts"; // API endpoint for posts
    const createPostForm = document.getElementById("create-post-form");
    const showPostBtn = document.getElementById("show-create-post-btn");
    const cancelPostBtn = document.getElementById("cancel-create-post-btn");

    /**
     * Fetches posts from the backend and displays them.
     */
    const fetchAndDisplayPosts = async () => {
      try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Network response was not ok");
        const posts = await response.json(); // Now using real data

        discussList.innerHTML = ""; // Clear existing

        if (posts.length === 0) {
          discussList.innerHTML =
            '<p class="placeholder-content">No discussions yet. Start one!</p>';
          return;
        }

        posts.forEach((post) => {
          const postItem = document.createElement("div");
          postItem.className = "discuss-item";

          // Create tags HTML
          const tagsHtml = post.tags
            .map((tag) => `<span class="tag">${tag}</span>`)
            .join("");

          postItem.innerHTML = `
                <div class="post-details">
                    <h4><a href="discuss-post.html?id=${post.id}">${post.title}</a></h4>
                    <div class="post-tags">
                        ${tagsHtml}
                    </div>
                </div>
                <div class="post-stats">
                    <div>
                        <span class="stat-count">${post.votes}</span> Votes
                    </div>
                    <div>
                        <span class="stat-count">${post.replies}</span> Replies
                    </div>
                     <div>
                        <span class="stat-count">${post.views}</span> Views
                    </div>
                </div>
            `;
          discussList.appendChild(postItem);
        });
      } catch (error) {
        console.error("Failed to fetch posts:", error);
        discussList.innerHTML =
          "<p class='placeholder-content'>Error loading posts. Please try again.</p>";
      }
    };

    // --- Form Logic for creating a post ---
    if (showPostBtn) {
      showPostBtn.addEventListener("click", () => {
        createPostForm.classList.remove("hidden");
        showPostBtn.style.display = "none";
      });
    }
    if (cancelPostBtn) {
      cancelPostBtn.addEventListener("click", () => {
        createPostForm.classList.add("hidden");
        showPostBtn.style.display = "block";
        createPostForm.reset();
      });
    }

    if (createPostForm) {
      // Find the form fields once
      const titleInput = document.getElementById("post-title");
      const contentInput = document.getElementById("post-content");
      const tagsInput = document.getElementById("post-tags");

      createPostForm.addEventListener("submit", async (e) => {
        e.preventDefault(); // Stop the form from reloading the page

        // 1. Create the data object from the form
        const newPostData = {
          title: titleInput.value,
          content: contentInput.value,
          tags: tagsInput.value,
        };

        // 2. Call the backend API
        try {
          const response = await fetch(API_URL, {
            // API_URL is '/api/posts'
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(newPostData),
          });

          if (response.ok) {
            // 3. If successful, hide form and refresh the list
            createPostForm.reset();
            createPostForm.classList.add("hidden");
            showPostBtn.style.display = "block";
            fetchAndDisplayPosts(); // <-- This refreshes the list!
          } else {
            // 4. Handle errors
            alert("Failed to create post. Please check your input.");
          }
        } catch (error) {
          console.error("Error creating post:", error);
          alert("An error occurred. Please try again.");
        }
      });
    }

    // Initial load for the discuss page
    fetchAndDisplayPosts();
  }

  // --- PROBLEM LIST PAGE LOGIC ---
  const problemListBody = document.getElementById("problem-list-body");
  if (problemListBody) {
    const API_URL = "/api/problems";

    const fetchAndDisplayProblems = async () => {
      try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Network error");
        const problems = await response.json();

        problemListBody.innerHTML = ""; // Clear table

        if (problems.length === 0) {
          problemListBody.innerHTML =
            '<tr><td colspan="4">No problems found.</td></tr>';
          return;
        }

        problems.forEach((problem) => {
          const row = document.createElement("tr");

          // Add difficulty class
          let difficultyClass = "";
          if (problem.difficulty === "Easy")
            difficultyClass = "difficulty-easy";
          if (problem.difficulty === "Medium")
            difficultyClass = "difficulty-medium";
          if (problem.difficulty === "Hard")
            difficultyClass = "difficulty-hard";

          row.innerHTML = `
            <td></td>
            <td>
              <a href="problem-detail.html?id=${problem.id}">${problem.title}</a>
            </td>
            <td>${problem.topic}</td>
            <td class="${difficultyClass}">${problem.difficulty}</td>
          `;
          problemListBody.appendChild(row);
        });
      } catch (error) {
        console.error("Failed to fetch problems:", error);
        problemListBody.innerHTML =
          '<tr><td colspan="4">Error loading problems.</td></tr>';
      }
    };

    fetchAndDisplayProblems();
  }

  // --- PROBLEM DETAIL PAGE LOGIC ---
  // (This is the all-new logic for the editor page)
  const editorDiv = document.getElementById("code-editor");
  if (editorDiv) {
    let editor;
    let problemData; // To store starter code
    const langSelect = document.getElementById("language-select");
    const outputConsole = document.getElementById("output-console"); // Get console

    // 1. Initialize the Ace Editor
    editor = ace.edit("code-editor");
    editor.setOptions({
      fontSize: "1rem",
      autoScrollEditorIntoView: true,
      copyWithEmptySelection: true,
    });

    // 2. Function to set theme based on light/dark mode
    const setEditorTheme = () => {
      if (document.body.classList.contains("dark-mode")) {
        editor.setTheme("ace/theme/monokai");
      } else {
        editor.setTheme("ace/theme/chrome");
      }
    };

    // 3. Function to change editor language
    const setLanguage = (lang) => {
        let mode = "ace/mode/java";
        if (lang === "python") mode = "ace/mode/python";
        if (lang === "cpp") mode = "ace/mode/c_cpp";

        editor.session.setMode(mode);
        // Set the starter code for the selected language
        if (problemData) {
            editor.setValue(problemData.starterCode[lang] || "", -1);
        }
    };

    // 4. Fetch the problem details from the API
    const loadProblem = async () => {
        try {
            // Get the ID from the URL (e.g., ?id=1)
            const urlParams = new URLSearchParams(window.location.search);
            const problemId = urlParams.get('id');

            if (!problemId) {
                document.getElementById("problem-title").innerText = "Error";
                document.getElementById("problem-body").innerHTML = "<p>No problem ID specified.</p>";
                return;
            }

            const response = await fetch(`/api/problems/${problemId}`);
            if (!response.ok) throw new Error("Problem not found");

            problemData = await response.json(); // Save data

            // Populate the description pane
            document.getElementById("problem-title").innerText = problemData.title;
            document.getElementById("problem-difficulty-badge").innerText = problemData.difficulty;
            document.getElementById("problem-body").innerHTML = problemData.description;

            // Set initial language and theme
            setLanguage(langSelect.value);
            setEditorTheme();

        } catch (error) {
            console.error("Failed to load problem:", error);
            document.getElementById("problem-title").innerText = "Error";
            document.getElementById("problem-body").innerHTML = `<p>${error.message}</p>`;
        }
    };

    // 5. Add event listeners
    langSelect.addEventListener("change", (e) => setLanguage(e.target.value));

    // Listen to theme changes to update editor
    themeToggleBtn.addEventListener("click", setEditorTheme);

    // --- UPDATED "RUN" BUTTON LISTENER ---
    document.getElementById("run-btn").addEventListener("click", async () => {
        const code = editor.getValue();
        const language = langSelect.value;
        const urlParams = new URLSearchParams(window.location.search);
        const problemId = urlParams.get('id');

        // 1. Show loading state
        outputConsole.innerHTML = `<div class="status">Running...</div>`;

        const requestBody = {
            problemId: parseInt(problemId),
            language: language,
            code: code
        };

        try {
            // 2. Call your backend API
            const response = await fetch("/api/run", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error("Network response was not ok");
            }

            const result = await response.json();

            // 3. Display the result
            let statusClass = "status-error";
            if (result.status === "Accepted") {
                statusClass = "status-accepted";
            }

let outputHtml = `
    <div class="results-panel">
        <div class="status-badge ${statusClass}">
            ${result.status}
        </div>

        ${(result.yourOutput !== undefined || result.expectedOutput !== undefined) ? `
            <div class="output-section">
                <div class="output-box">
                    <div class="output-label">Your Output</div>
                    <pre>${result.yourOutput || result.output || ''}</pre>
                </div>
                <div class="output-box">
                    <div class="output-label">Expected Output</div>
                    <pre>${result.expectedOutput || ''}</pre>
                </div>
            </div>
        ` : ''}

        ${result.error ? `
            <div class="output-box" style="margin-top: 1rem; border-left-color: #e53e3e;">
                <div class="output-label" style="color: #e53e3e;">Error</div>
                <pre>${result.error}</pre>
            </div>
        ` : ''}
    </div>
`;

outputConsole.innerHTML = outputHtml;

        } catch (error) {
            console.error("Error running code:", error);
            outputConsole.innerHTML = `<div class="status status-error">Error</div><pre>${error.message}</pre>`;
        }
    });

    // "Submit" button is still a placeholder
    document.getElementById("submit-btn").addEventListener("click", async () => {
        const code = editor.getValue();
        const language = langSelect.value;
        const urlParams = new URLSearchParams(window.location.search);
        const problemId = parseInt(urlParams.get('id'));

        outputConsole.innerHTML = `
            <div class="status status-running">
                <div style="font-size: 1.2rem; font-weight: 700;">Submitting...</div>
                <div>Running test cases (3/3)</div>
            </div>
        `;

        try {
            const response = await fetch("/api/submit", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ problemId, language, code })
            });

            const result = await response.json();

            const statusClass = result.status === "Accepted" ? "status-accepted" :
                              result.status === "Partial" ? "status-partial" : "status-wrong";

            outputConsole.innerHTML = `
                <div class="verdict-container">
                    <div class="verdict-badge ${statusClass}">
                        ${result.status} (${result.score}%)
                    </div>
                    <div style="font-size: 1.3rem; color: #94a3b8; margin-top: 1rem;">
                        Submission ID: ${result.submissionId}
                    </div>
                </div>
            `;

        } catch (error) {
            outputConsole.innerHTML = `<div class="status status-error">Submit failed: ${error.message}</div>`;
        }
    });


    // 6. Initial Load
    loadProblem();
  }

}); // <-- This is the end of your main DOMContentLoaded listener