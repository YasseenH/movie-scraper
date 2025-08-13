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
}

function setupFormLoading() {
  const forms = document.querySelectorAll("form");
  forms.forEach((form) => {
    form.addEventListener("submit", function () {
      const submitBtn = this.querySelector('button[type="submit"]');
      if (submitBtn) {
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="loading">Searching...</span>';
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

    const filtered = movieTitles
      .filter((title) => title.toLowerCase().includes(value.toLowerCase()))
      .slice(0, 2);

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
    filterAndShowSuggestions(this.value);
  });

  searchInput.addEventListener("focus", function () {
    if (this.value.length > 0) {
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
