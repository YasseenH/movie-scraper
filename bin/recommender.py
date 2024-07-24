import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer 
from sklearn.metrics.pairwise import cosine_similarity

# Read CSV File
df = pd.read_csv("MoviesData.csv")

# Select Features
features = ['genre', 'actors', 'imdb_rating', 'plot']

#To handle missing values in the selected features and drop the rows with missing values
for feature in features:
    df[feature] = df[feature].fillna('')
df = df.dropna(how='any',axis=0) 

# Create a column in DF which combines all selected features
def combine_features(row):
    try:
        return str(row['imdb_rating']) + " " + row['actors'] + " " + row['genre'] + " " + row['plot']
    except Exception as e:
        print("Error:", e)
        print("Error:", row)


df["combined_features"] = df.apply(combine_features, axis=1)

# Create count matrix from this new combined column and compute the cosine similarity based on it
cv = CountVectorizer()
count_matrix = cv.fit_transform(df["combined_features"])
cosine_sim = cosine_similarity(count_matrix)

# Get the title of the movie from the index
df['index'] = df.index
def get_title_from_index(index):
    return df[df.index == index]["title"].values[0]

# Get the movie title from the user (input part to be used in the web app)
movie_user_likes = "The Dark Knight"

# Find the index of the movie in the dataframe
movie_index = df[df.title == movie_user_likes]["index"].values[0]

# Enumerate through all the similarity scores of the movie to make a tuple of movie index and similarity scores
similar_movies = list(enumerate(cosine_sim[movie_index]))

# Sort the list of similar movies according to the similarity scores in descending order
sorted_similar_movies = sorted(similar_movies, key=lambda x:x[1], reverse=True)

# Print titles of first 50 movies in the list of similar movies (excluding empty rows)
i = 0
for movie in sorted_similar_movies:
    if movie[0] < len(df) and not df[df.index == movie[0]].empty:
        print(get_title_from_index(movie[0]))
        i = i + 1
        if i > 50:
            break
