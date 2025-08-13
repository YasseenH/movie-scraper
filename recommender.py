from pandas import DataFrame as df
from pandas import read_csv
from sklearn.feature_extraction.text import CountVectorizer 
from sklearn.metrics.pairwise import cosine_similarity

df = read_csv("MoviesData.csv")

features = ['genre', 'actors', 'imdb_rating', 'plot']

for feature in features:
    df[feature] = df[feature].fillna('')
df = df.dropna(how='any',axis=0) 

def combine_features(row):
    try:
        return str(row['imdb_rating']) + " " + row['actors'] + " " + row['genre'] + " " + row['plot']
    except Exception as e:
        print("Error:", e)
        print("Error:", row)

df["combined_features"] = df.apply(combine_features, axis=1)

cv = CountVectorizer()
count_matrix = cv.fit_transform(df["combined_features"])
cosine_sim = cosine_similarity(count_matrix)

df['index'] = df.index
def get_title_from_index(index):
    return df[df.index == index]["title"].values[0]

def get_movie_recommendation(movie_user_likes):
    df["title"] = df["title"].str.lower()

    if movie_user_likes.lower() not in df['title'].values:
        return []

    movie_index = df[df.title == movie_user_likes.lower()]["index"].values[0]

    similar_movies = list(enumerate(cosine_sim[movie_index]))

    sorted_similar_movies = sorted(similar_movies, key=lambda x:x[1], reverse=True)

    recommended_movies = []
    i = 0
    for movie in sorted_similar_movies:
        if movie[0] < len(df) and not df[df.index == movie[0]].empty:
            temp = []
            temp.append(df[df.index == movie[0]]['title'].values[0])
            temp.append(str(df[df.index == movie[0]]['release_year'].values[0]))
            temp.append(str(df[df.index == movie[0]]['imdb_rating'].values[0]))
            temp.append(df[df.index == movie[0]]['mpaa_rating'].values[0])
            temp.append(df[df.index == movie[0]]['genre'].values[0])
            temp.append(df[df.index == movie[0]]['plot'].values[0])
            temp.append(df[df.index == movie[0]]['actors'].values[0])
            temp.append(df[df.index == movie[0]]['runtime'].values[0])
            temp.append(df[df.index == movie[0]]['imdb_link'].values[0])
            recommended_movies.append(temp)

            i += 1
            if i > 10:
                break
    return recommended_movies