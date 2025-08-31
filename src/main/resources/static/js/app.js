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

  // --- STUDY GROUP PAGE LOGIC ---
  // This code only runs if it finds the 'groups-list' element,
  // meaning we are on the Study Groups page (index.html).
  const groupsList = document.getElementById("groups-list");
  if (groupsList) {
    const createGroupForm = document.getElementById("create-group-form");
    const showFormBtn = document.getElementById("show-create-form-btn");
    const cancelBtn = document.getElementById("cancel-create-btn");
    const API_URL = "/api/groups";

    /**
     * Fetches study groups from the backend and displays them.
     */
    const fetchAndDisplayGroups = async () => {
      try {
        const response = await fetch(API_URL);
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const groups = await response.json();

        groupsList.innerHTML = ""; // Clear existing list

        if (groups.length === 0) {
          groupsList.innerHTML =
            "<p>No study groups found. Why not create one?</p>";
          return;
        }

        groups.forEach((group) => {
          const card = document.createElement("div");
          card.className = "group-card";
          card.innerHTML = `
                        <h3>${group.name}</h3>
                        <p>${group.description}</p>
                        <button onclick="joinGroup(${group.id})">Join Group</button>
                    `;
          groupsList.appendChild(card);
        });
      } catch (error) {
        console.error("Failed to fetch groups:", error);
        groupsList.innerHTML =
          "<p>Error loading study groups. Please try again later.</p>";
      }
    };

    /**
     * Handles the submission of the "Create Group" form.
     */
    createGroupForm.addEventListener("submit", async (event) => {
      event.preventDefault();

      const nameInput = document.getElementById("group-name");
      const descriptionInput = document.getElementById("group-description");
      const newGroup = {
        name: nameInput.value,
        description: descriptionInput.value,
      };

      try {
        const response = await fetch(API_URL, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(newGroup),
        });

        if (response.ok) {
          createGroupForm.reset();
          createGroupForm.classList.add("hidden");
          showFormBtn.style.display = "block";
          fetchAndDisplayGroups(); // Refresh the list
        } else {
          alert("Failed to create group. Please check your input.");
        }
      } catch (error) {
        console.error("Error creating group:", error);
        alert("An error occurred. Please try again.");
      }
    });

    // Event listeners for showing/hiding the form
    showFormBtn.addEventListener("click", () => {
      createGroupForm.classList.remove("hidden");
      showFormBtn.style.display = "none";
    });

    cancelBtn.addEventListener("click", () => {
      createGroupForm.classList.add("hidden");
      showFormBtn.style.display = "block";
      createGroupForm.reset();
    });

    // Dummy function for joining a group
    window.joinGroup = (groupId) => {
      alert(
        `Joining group with ID: ${groupId}. (Functionality to be implemented)`
      );
    };

    // Initial load for the study groups page
    fetchAndDisplayGroups();
  }

  // You can add more page-specific logic using the same `if` pattern, e.g.:
  // if (document.querySelector('.problems-table')) {
  //     // Code for the problems.html page
  // }
});
