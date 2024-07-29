from flask import Flask, render_template, request
from waitress import serve
from recommender import get_movie_recommendation

app = Flask(__name__)

@app.route('/')
@app.route('/index')
def index():
    return render_template('index.html')

@app.route('/recommender')
def show_recommendation():
    original_movie = request.args.get('movie')
    movie_recommendation = get_movie_recommendation(original_movie)
    return render_template(
        "movie.html",
        title = original_movie,
        rec_name = movie_recommendation[0][0],
        rec_year = movie_recommendation[0][1],
        rec_rating = movie_recommendation[0][2],
        rec_mpaa = movie_recommendation[0][3],
        rec_genre = movie_recommendation[0][4],
        rec_plot = movie_recommendation[0][5],
        rec_cast = movie_recommendation[0][6],
        rec_runtime = movie_recommendation[0][7],
    )

if __name__ == "__main__":
    serve(app, host="0.0.0.0", port= 8000)