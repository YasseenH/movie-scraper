from flask import Flask, render_template, request
from waitress import serve
from recommender import get_movie_recommendation

app = Flask(__name__)

# Home page
@app.route('/')
def index():
    title = "Movie Recommender"
    return render_template('movie.html', title=title, recommendations=[])

# Capitalize title of the movie
def capitalize_title(title):
    return ' '.join(word.capitalize() for word in title.split())

# Recommendation page
@app.route('/recommender', methods=['GET'])
def recommender():
    title = "Movie Recommender"
    movie_name = request.args.get('movie')

    # If no movie name is provided, default to "Whiplash", my favorite movie :)
    if not bool(movie_name.strip()):
        movie_name = "Whiplash"

    movie_recommendation = get_movie_recommendation(movie_name)
    
    if not movie_recommendation:
        return render_template('movie-not-found.html', title=title, recommendations=[])
    
    recommendations = [
        {
            'rec_name': capitalize_title(movie[0]),
            'rec_year': movie[1],
            'rec_rating': movie[2],
            'rec_mpaa': movie[3],
            'rec_genre': movie[4],
            'rec_plot': movie[5],
            'rec_cast': movie[6],
            'rec_runtime': movie[7],
            'rec_link': movie[8] 
        }
        for movie in movie_recommendation
    ]
    
    return render_template('movie.html', title=title, recommendations=recommendations)

# Run the app
if __name__ == "__main__":
    print("Server is running")
    serve(app, host="0.0.0.0", port= 8000)