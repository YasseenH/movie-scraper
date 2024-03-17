import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MovieManager {

    private ArrayList<Movie> movies;

    public MovieManager() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("ScrapedData.txt"));
        while (sc.hasNextLine()) {
            String tempEnd = sc.nextLine();
            String allDetails = tempEnd;
            while (!tempEnd.equals("")) {
                tempEnd = sc.nextLine();
                allDetails += "\n" + tempEnd;
            }
            sc.nextLine();
            System.out.println(allDetails);
            Movie tempMovie = new Movie(allDetails);
            if (!movies.contains(tempMovie)) {
                movies.add(tempMovie);
            }
            System.out.println(movies);
        }
        sc.close();
    }

    /**
     * 
     * @param search the user's search 
     * @return a list with
     */
    public ArrayList<Movie> partialSearch(String search) {
        ArrayList<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getTitle().contains(search)) {
                result.add(m);
            }
        }
        return result;
    }

    //TODO Double check this works
    public ArrayList<Movie> sortBy(String sortValue) {
        ArrayList<Movie> result = deepCopyList(movies);
        if (sortValue.toLowerCase().equals("duration")) {
            Collections.sort(result, new CompareMovieByDuration());
            return result;
        }
        else if (sortValue.toLowerCase().equals("year")) {
            Collections.sort(result, new CompareMovieByYear());
            return result;
        }
        else if (sortValue.toLowerCase().equals("rating")) {
            CompareMovieByRating sort = new CompareMovieByRating();
            Collections.sort(result, sort);
            return result;
        }
        return result;
    }

    /**
     * Deep copies an arraylist
     * @param list that is deep copied
     * @return a newly deep copied arrayList
     */
    private ArrayList<Movie> deepCopyList(ArrayList<Movie> list) {
        ArrayList<Movie> result = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            result.add(movies.get(i));
        }
        return result;
    }

    /**
     * Class that compares Movies based on the duration
     */
    public class CompareMovieByDuration implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return o1.getTime(o1.getDuration()) - o2.getTime(o2.getDuration());
        }
    }
    
    /**
     * Class that compares Movies based on the Year
     */
    public class CompareMovieByYear implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return o1.getReleaseYear() - o2.getReleaseYear();
        }
    }
    
    /**
     * Class that compares Movies based on the Rating
     */
    public class CompareMovieByRating implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return (int) (o1.getFanRating() - o2.getFanRating());
        }
    }
}