# MovieScraper - AI-Powered Movie Recommendations

A modern web application that provides personalized movie recommendations using machine learning. Built with Flask, scikit-learn, and a clean, responsive UI.

![MovieScraper Demo](https://github.com/user-attachments/assets/439062ca-7c9c-499c-b5c7-d28d1b1ab239)



## Features

- ** Smart Recommendations**: ML-powered suggestions based on movie preferences
- ** Autocomplete Search**: Dynamic search with intelligent suggestions
- ** Responsive Design**: Beautiful UI that works on all devices
- ** Fast Performance**: Optimized for quick recommendations
- ** Modern Interface**: Clean, professional design with smooth interactions

## Live Demo

Visit the live application: [Live Website](https://movie-scraper-production-133f.up.railway.app)

## Tech Stack

- **Backend**: Flask (Python)
- **Frontend**: HTML5, CSS3, JavaScript
- **Machine Learning**: scikit-learn, pandas
- **Server**: Waitress (WSGI)
- **Styling**: Custom CSS with modern design system

## Project Structure

```
MovieScraper/
├── server.py              # Flask application
├── recommender.py         # ML recommendation engine
├── MoviesData.csv         # Movie dataset
├── requirements.txt       # Python dependencies
├── static/
│   ├── css/style.css     # Main stylesheet
│   └── js/app.js         # Frontend JavaScript
└── templates/
    ├── index.html         # Home page
    ├── movie.html         # Results page
    └── movie-not-found.html # Error page
```

## How It Works

1. **Input**: User searches for a movie they enjoy
2. **Analysis**: ML algorithm analyzes genre, actors, ratings, and plot
3. **Recommendations**: System finds similar movies using cosine similarity
4. **Results**: Clean, organized display of movie suggestions

## Quick Start

### Prerequisites

- Python 3.8 or higher
- pip (Python package installer)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/moviewebscraper-recommender.git
   cd moviewebscraper-recommender
   ```

2. **Set up virtual environment:**

   ```bash
   python3 -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   ```

3. **Install dependencies:**

   ```bash
   pip install -r requirements.txt
   ```

4. **Run the application:**

   ```bash
   python server.py
   ```

5. **Open your browser:**
   Navigate to `http://localhost:8000`

-------------------------------------
**Made for movie lovers everywhere!**
