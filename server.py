from flask import Flask, render_template, request
from waitress import serve

app = Flask(__name__)

@app.route('/')
@app.route('/index')
def index():
    return render_template('index.html')

@app.route('/recommender')
def get_recommendation():
    # original_movie = request.args.get('movie')
    #THIS WHERE THE GET MOVIE RECOMMENDATION FUNCTION GOES "movie_recommendation = get_movie_recommendation(original_movie)"
    return render_template(
        "movie.html",
        title = "MOVIE NAME",
        rec_name = "MOVIE REC. NAME",
        cast = "CAST REC.",
        plot = "PLOT REC.",
        rating = "RATING REC.",
        review = "REVIEW REC.",
        genre = "GENRE REC."
    )

if __name__ == "__main__":
    serve(app, host="0.0.0.0", port= 8000)