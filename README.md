# Movie Web Scraper/Recommender

Java-based application for a Movie Web Scraping project and a Python-based application for Movie Recommendation. 

The goal is to scrape movie information from websites like IMDb, organize it in a .txt and .csv files, and 
offer users a user-friendly interface to allow the user to get a movie recommendation based on the given movie using our scraped dataset.
#
Learned how to:
- Read a website's HTML and CSS tags
- Parse through repeated generic elements using for each loops
- Parsed the website using tags, IDs, and classes in order to specify scrapped elements
- Comparator and how to implement different sorts based on different types (i.e rating, duration, etc.)
- Create a .csv file in Java
- Use Pandas to manipulate a .csv file and do different functions (removing null values, adding a index column)
- Use sklearn to create a cosine similarity matrix based on inputed text
#
What's Next:
- Implement the UI using Flask
- Implement features that allow recommendations to be given based on certain attributes of the movie
- Allow the user to sort the results of the recommendations based on prefences like rating, genre, and mpaa rating
