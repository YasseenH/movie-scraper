import java.io.FileNotFoundException;

public class MovieSearcher {
    public static void main(String[] args) throws FileNotFoundException {
        MovieManager manager = new MovieManager();
        System.out.println(manager.partialSearch("h"));
    }
}
