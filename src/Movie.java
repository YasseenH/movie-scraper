import java.util.ArrayList;

public class Movie {
	private final String TITLE;
	private final int YEAR;
	private final ArrayList<String> CAST;
	private final String GENRE;
	private final String DESCRIPTION;
	private final double RATING;
	private final String FILM_RATING;
	private final String DURATION;
	
	public Movie(String title, int year, String filmRating, double fanRating, String duration, ArrayList<String> cast, String genre, String description) {
		TITLE = title;
		YEAR = year;
		CAST = cast;
		FILM_RATING = filmRating;
		GENRE = genre;
		DESCRIPTION = description;
		RATING = fanRating;
		DURATION = duration;
	}


	/**
	 * @return the rating
	 */
	public double getRating() {
		return RATING;
	}
	
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return DURATION;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return TITLE;
	}


	/**
	 * @return the year
	 */
	public int getReleaseYear() {
		return YEAR;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return DESCRIPTION;
	}
	
	public String getCastList() {
		StringBuilder result = new StringBuilder();
		result.append(CAST.get(0));
		for (int i = 1; i < CAST.size(); i++) {
			result.append(",");
			result.append(CAST.get(i));
		}
		return result.toString();
	}
	
	public boolean isActorHere(String actor) {
		String realName = actor.toLowerCase();
		for (int i = 0; i < CAST.size(); i++) {
			if (CAST.get(i).toLowerCase().equals(realName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return a string representation of all the movie's details
	 */
	public String toString() {
		return "Title: " + this.getTitle() + "\nYear: " + this.getReleaseYear() + "\nRating: " + this.getRating() + "\nFilm Rating: " + FILM_RATING
		+ "\nGenre: " + GENRE + "\nDescription: " + DESCRIPTION + "\nCast: " + getCastList() + "\nDuration:" + DURATION + "\n";
	}
}
