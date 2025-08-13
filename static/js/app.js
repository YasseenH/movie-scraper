// MovieScraper App JavaScript

document.addEventListener("DOMContentLoaded", function () {
  initApp();
});

function initApp() {
  setupFormLoading();
  setupSearchSuggestions();
  setupSmoothScrolling();
  setupKeyboardNavigation();
  setupFocusManagement();
  setupPageLoadHandling();
}

function setupPageLoadHandling() {
  // Prevent form resubmission on page refresh
  if (
    window.performance &&
    window.performance.navigation.type ===
      window.performance.navigation.TYPE_BACK_FORWARD
  ) {
    // User navigated back/forward, clear any form states
    const forms = document.querySelectorAll("form");
    forms.forEach((form) => {
      form.reset();
    });

    // Clear any loading states
    const submitBtns = document.querySelectorAll('button[type="submit"]');
    submitBtns.forEach((btn) => {
      if (btn.disabled) {
        btn.disabled = false;
        btn.innerHTML = btn.getAttribute("data-original-text") || "Find Movies";
      }
    });
  }

  // Handle page visibility changes (when user switches tabs and comes back)
  document.addEventListener("visibilitychange", function () {
    if (document.visibilityState === "visible") {
      // User came back to the page, ensure forms are in clean state
      const submitBtns = document.querySelectorAll('button[type="submit"]');
      submitBtns.forEach((btn) => {
        if (btn.disabled) {
          btn.disabled = false;
          btn.innerHTML =
            btn.getAttribute("data-original-text") || "Find Movies";
        }
      });
    }
  });
}

function setupFormLoading() {
  const forms = document.querySelectorAll("form");
  forms.forEach((form) => {
    form.addEventListener("submit", function (e) {
      const submitBtn = this.querySelector('button[type="submit"]');
      if (submitBtn) {
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="loading">Searching...</span>';
      }

      // Store the search query in sessionStorage for better back button handling
      const searchInput = this.querySelector('input[name="movie"]');
      if (searchInput && searchInput.value.trim()) {
        sessionStorage.setItem("lastSearchQuery", searchInput.value.trim());
      }
    });
  });
}

function setupSearchSuggestions() {
  const searchInput = document.querySelector(".search-input");
  if (!searchInput) return;

  const suggestionsContainer = document.createElement("div");
  suggestionsContainer.className = "search-suggestions";
  suggestionsContainer.style.display = "none";

  const searchForm = searchInput.closest(".search-form");
  if (searchForm) {
    searchForm.style.position = "relative";
    searchForm.appendChild(suggestionsContainer);

    const inputRect = searchInput.getBoundingClientRect();
    const inputWidth = inputRect.width;

    suggestionsContainer.style.width = inputWidth + "px";
    suggestionsContainer.style.left = "0px";
  } else {
    searchInput.parentNode.appendChild(suggestionsContainer);
  }

  let movieTitles = [];

  async function fetchMovieTitles() {
    try {
      const response = await fetch("/api/movie-titles");
      const data = await response.json();
      movieTitles = data.titles || [];
    } catch (error) {
      console.log("Error fetching movie titles:", error);
      movieTitles = ["Whiplash", "Inception", "The Shawshank Redemption"];
    }
  }

  fetchMovieTitles();

  function filterAndShowSuggestions(value) {
    if (value.length === 0) {
      suggestionsContainer.style.display = "none";
      return;
    }

    const searchValue = value.toLowerCase().trim();

    // Better filtering: prioritize exact matches and starts-with matches
    const filtered = movieTitles
      .filter((title) => {
        const titleLower = title.toLowerCase();
        // First priority: titles that start with the search value
        if (titleLower.startsWith(searchValue)) {
          return true;
        }
        // Second priority: titles that contain the search value
        if (titleLower.includes(searchValue)) {
          return true;
        }
        return false;
      })
      .sort((a, b) => {
        const aLower = a.toLowerCase();
        const bLower = b.toLowerCase();

        // Sort by relevance: starts-with first, then contains
        const aStartsWith = aLower.startsWith(searchValue);
        const bStartsWith = bLower.startsWith(searchValue);

        if (aStartsWith && !bStartsWith) return -1;
        if (!aStartsWith && bStartsWith) return 1;

        // If both have same priority, sort alphabetically
        return aLower.localeCompare(bLower);
      })
      .slice(0, 3);

    if (filtered.length > 0) {
      suggestionsContainer.innerHTML = filtered
        .map((title) => `<div class="suggestion-item">${title}</div>`)
        .join("");
      suggestionsContainer.style.display = "block";
    } else {
      suggestionsContainer.style.display = "none";
    }
  }

  searchInput.addEventListener("input", function () {
    // Only show suggestions for meaningful input (at least 2 characters)
    if (this.value.trim().length >= 2) {
      filterAndShowSuggestions(this.value);
    } else {
      suggestionsContainer.style.display = "none";
    }
  });

  searchInput.addEventListener("focus", function () {
    // Only show suggestions if there's meaningful input (at least 2 characters)
    if (this.value.trim().length >= 2) {
      filterAndShowSuggestions(this.value);
    }
  });

  searchInput.addEventListener("blur", function () {
    setTimeout(() => {
      suggestionsContainer.style.display = "none";
    }, 200);
  });

  suggestionsContainer.addEventListener("click", function (e) {
    if (e.target.classList.contains("suggestion-item")) {
      searchInput.value = e.target.textContent;
      suggestionsContainer.style.display = "none";
      searchInput.focus();
    }
  });
}

function setupSmoothScrolling() {
  const links = document.querySelectorAll('a[href^="#"]');
  links.forEach((link) => {
    link.addEventListener("click", function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute("href"));
      if (target) {
        target.scrollIntoView({ behavior: "smooth" });
      }
    });
  });
}

function setupKeyboardNavigation() {
  const searchInput = document.querySelector(".search-input");
  if (!searchInput) return;

  searchInput.addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
      this.form.submit();
    } else if (e.key === "Escape") {
      this.blur();
    }
  });
}

function setupFocusManagement() {
  const searchInput = document.querySelector(".search-input");
  if (searchInput) {
    searchInput.addEventListener("focus", function () {
      this.parentElement.classList.add("focused");
    });

    searchInput.addEventListener("blur", function () {
      this.parentElement.classList.remove("focused");
    });
  }

  // Handle browser back button and prevent form resubmission
  if (window.history && window.history.pushState) {
    window.addEventListener("popstate", function () {
      // Clear any loading states when going back
      const submitBtns = document.querySelectorAll('button[type="submit"]');
      submitBtns.forEach((btn) => {
        if (btn.disabled) {
          btn.disabled = false;
          btn.innerHTML =
            btn.getAttribute("data-original-text") || "Find Movies";
        }
      });

      // Restore search input value if available
      const searchInput = document.querySelector(".search-input");
      if (searchInput) {
        const lastQuery = sessionStorage.getItem("lastSearchQuery");
        if (lastQuery) {
          searchInput.value = lastQuery;
        }
      }
    });

    // Store original button text to restore later
    const submitBtns = document.querySelectorAll('button[type="submit"]');
    submitBtns.forEach((btn) => {
      if (!btn.getAttribute("data-original-text")) {
        btn.setAttribute("data-original-text", btn.innerHTML);
      }
    });
  }
}

function showNotification(message, type = "info") {
  const notification = document.createElement("div");
  notification.className = `notification notification-${type}`;
  notification.textContent = message;

  document.body.appendChild(notification);

  setTimeout(() => {
    notification.remove();
  }, 3000);
}
