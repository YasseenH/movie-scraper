import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class MovieManager {

    private TreeMap<String, Movie> movies;

    public MovieManager() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("ScrapedData.txt"));
        while (sc.hasNextLine()) {
            String tempEnd = sc.nextLine();
            String allDetails = tempEnd;
            while (!tempEnd.equals("")) {
                tempEnd = sc.nextLine();
                allDetails += "\n" + tempEnd;
            }
            System.out.println(allDetails);
            Movie tempMovie = new Movie(allDetails);
            movies.put(tempMovie.getTitle(), tempMovie);
            System.out.println(allDetails);
        }
        
        //TODO fix this 
        Collections.sort(movies);
        sc.close();
    }

    /**
     * 
     * @param search the user's search 
     * @return a map with
     */
    public TreeMap<String, Movie> partialSearch(String search) {
        TreeMap<String, Movie> result = new TreeMap<>();
        for (String key : movies.keySet()) {
            if (key.contains(search)) {
                result.put(key, movies.get(key));
            }
        }
        return result;
    }

    //TODO fix this
    public TreeMap<String, Movie> sortBy(String sortValue) {
        TreeMap<String, Movie> result = deepCopyMap(movies);
        if (sortValue.toLowerCase().equals("duration")) {
            Collections.sort(result, new compareMovieByDuration());
            return result;
        }
        else if (sortValue.toLowerCase().equals("year")) {
            Collections.sort(result, new compareMovieByYear());
            return result;
        }
        else if (sortValue.toLowerCase().equals("rating")) {
            Collections.sort(result, new compareMovieByRating());
            return result;
        }
        return result;
    }

    //TODO: Double check to see if there is a simplier way to do this with maps
    private TreeMap<String, Movie> deepCopyMap(TreeMap<String, Movie> map) {
        TreeMap<String, Movie> result = new TreeMap<>();
        for (String k : map.keySet()) {
            result.put(k, map.get(k));
        }
        return result;
    }

    public class compareMovieByDuration implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return 0;
        }
    }

    public class compareMovieByYear implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return 0;
        }
    }

    public class compareMovieByRating implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return 0;
        }
    }
    
}
