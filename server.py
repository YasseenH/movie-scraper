from flask import Flask, render_template, request, jsonify
from waitress import serve
from recommender import get_movie_recommendation
import pandas as pd

app = Flask(__name__)

@app.route('/')
def index():
    title = "MovieScraper - Discover Your Next Favorite Movie"
    return render_template('index.html', title=title)

@app.route('/api/movie-titles')
def get_movie_titles():
    try:
        df = pd.read_csv("MoviesData.csv")
        titles = df['title'].dropna().unique().tolist()
        titles.sort()
        return jsonify({'titles': titles})
    except Exception as e:
        print(f"Error reading CSV: {e}")
        return jsonify({'titles': []}), 500

def capitalize_title(title):
    return ' '.join(word.capitalize() for word in title.split())

@app.route('/recommender', methods=['GET'])
def recommender():
    title = "Movie Recommender"
    movie_name = request.args.get('movie')

    if not movie_name or not movie_name.strip():
        movie_name = "Whiplash"

    movie_recommendation = get_movie_recommendation(movie_name)
    
    if not movie_recommendation:
        return render_template('movie-not-found.html', title=title, recommendations=[])
    
    filtered_recommendations = movie_recommendation[1:-1] if len(movie_recommendation) > 2 else []
    
    recommendations = [
        {
            'rec_name': capitalize_title(movie[0]),
            'rec_title': capitalize_title(movie[0]),
            'rec_year': movie[1],
            'rec_rating': movie[2],
            'rec_mpaa': movie[3],
            'rec_genre': movie[4],
            'rec_plot': movie[5],
            'rec_cast': movie[6],
            'rec_runtime': movie[7],
            'rec_link': movie[8] 
        }
        for movie in filtered_recommendations
    ]
    
    return render_template('movie.html', title=title, recommendations=recommendations)

if __name__ == "__main__":
    print("Server is running")
    serve(app, host="0.0.0.0", port= 8000)