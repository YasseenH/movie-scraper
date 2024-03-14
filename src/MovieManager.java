import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            System.out.println(allDetails);
            Movie tempMovie = new Movie(allDetails);
            movies.add(tempMovie);
            System.out.println(allDetails);
        }
        sc.close();
    }

    
}
