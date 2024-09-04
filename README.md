# Movie Web Scraper/Recommender

Welcome to the Movie Web Scraper/Recommender project! This repository houses a Java-based application for web scraping movie information and a Python-based application for generating personalized movie recommendations.

## Overview

The primary objective of this project is to scrape movie details from popular websites like IMDb, organize the data into .txt and .csv files, and provide a user-friendly interface for movie recommendations based on the scraped dataset.

## Features

### Web Scraping (Java)
- **HTML & CSS Parsing:** Learn how to read and interpret HTML and CSS tags.
- **Data Extraction:** Utilize loops and conditionals to parse repetitive elements and extract specific data using tags, IDs, and classes.
- **CSV Creation:** Generate .csv files to store the scraped data efficiently.

### Data Handling & Recommendation (Python)
- **Data Manipulation with Pandas:** Clean and process the scraped data by removing null values and adding index columns.
- **Cosine Similarity for Recommendations:** Employ sklearn to create a cosine similarity matrix for text-based movie recommendations.
- **Web Interface with Flask:** Build a dynamic web interface using Flask, integrating basic HTML and CSS files with Jinja2 templates.

## Project Learnings
- Effective HTML and CSS parsing techniques
- Advanced data extraction and sorting methods
- Comprehensive CSV file creation and manipulation
- Implementation of recommendation algorithms using cosine similarity
- Building dynamic web applications with Flask and Jinja2

## Future Enhancements
- **Attribute-Based Recommendations:** Develop features to provide recommendations based on specific movie attributes like genre, rating, and MPAA rating.
- **Advanced Sorting Options:** Enable users to sort recommendation results according to their preferences.
- **Expanded Movie Dataset:** Increase the number of movies in the `.csv` file to enhance the quality and breadth of recommendations.
- **Database Migration:** Migrate the movie dataset from a `.csv` file to a SQL database to improve data management, scalability, and query performance.

Stay tuned for more updates and features!

## Installation

To set up this project on your local machine, follow these steps:

### Prerequisites

- Java Development Kit (JDK)
- Python 3.6 or higher
- pip (Python package installer)

### Steps

1. **Clone the repository:**

    ```sh
    git clone https://github.com/yourusername/moviewebscraper-recommender.git
    cd moviewebscraper-recommender
    ```

2. **Set up the Java environment: (Optional)**

    - Make sure you have the JDK installed.
    - Compile the Java files (if you want to re-scrape the data, make sure to input the file's name).

3. **Set up the Python environment:**

    - It's recommended to use a virtual environment to avoid conflicts with other packages:

        ```sh
        python3 -m venv venv
        source venv/bin/activate  # On Windows, use `venv\Scripts\activate`
        ```

    - Install the required Python packages:

        ```sh
        pip install -r requirements.txt
        ```

4. **Run the scraper and the recommender system:**

    - Execute the Java scraper to generate the movie dataset. (Optional)
    - Run the Flask server:

        ```sh
        python server.py
        ```
    
    **Note if this doesn't work try using python3.

5. **Access the application:**

    - Open your web browser and go to `http://127.0.0.1:8000`.

That's it! You should now have the Movie Web Scraper/Recommender up and running on your local machine.
