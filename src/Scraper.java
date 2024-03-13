import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	private static ArrayList<Movie> allMovies = new ArrayList<>();
	private static final String[] LINKS = {"https://m.imdb.com/chart/top/", 
			"https://www.imdb.com/chart/moviemeter/", "https://www.imdb.com/chart/top-english-movies/?ref_=chtmvm_ql_4"};
	private static String movieTitle = "";
	private static String year = "";
	private static String filmRating = "";
	private static String duration = "";
	private static String fanRating;
	private static String genre;
	private static String description;
	private static ArrayList<String> cast = new ArrayList<>();
	
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			try {
				final Document mainDoc = Jsoup.connect(LINKS[i]).get();
				
				Elements movies = mainDoc.getElementsByClass("ipc-metadata-list-summary-item sc-10233bc-0 iherUv cli-parent");
				
				for (Element movieBlock : movies) {
					String link = "https://www.imdb.com" + movieBlock.getElementsByClass("ipc-title-link-wrapper").attr("href");
					Document subDoc = Jsoup.connect(link).get();
					movieTitle = subDoc.getElementsByClass("hero__primary-text").text();
					fanRating = subDoc.getElementsByClass("sc-bde20123-1 cMEQkK").first().text();
					description = subDoc.getElementsByClass("sc-466bb6c-2 chnFO").text();
					Elements genres = subDoc.getElementsByClass("ipc-chip-list__scroller");
					for (Element e : genres) {
						genre = e.getElementsByTag("a").text() + " ";
					}
					Elements allCastMembers = subDoc.getElementsByClass("ipc-sub-grid ipc-sub-grid--page-span-2 ipc-sub-grid--wraps-at-above-l ipc-shoveler__grid");
					Elements indiviualCast = allCastMembers.first().getElementsByTag("a");
					for (Element e : indiviualCast) {
						String tempMember = e.text();
						if (!tempMember.equals("")) {
							cast.add(tempMember);
						}
					}
					Elements tempScope = subDoc.getElementsByClass("ipc-inline-list ipc-inline-list--show-dividers sc-d8941411-2 cdJsTz baseAlt");
					for (Element e : tempScope) {
						String movieDetails = e.tagName("li").text();
						Scanner sc = new Scanner(movieDetails);
						year = sc.next();
						String temp = sc.next();
						if (isfilmRating(temp)) {
							filmRating = temp;
						}
						else {
							filmRating = "None";
							duration = temp;
						}
						if (filmRating.equals("Not")) {
							filmRating += " " + sc.next();
						}
						while(sc.hasNext()) {
							duration += " " + sc.next();
						}
						int realYear = Integer.parseInt(year);
						double realRating = Double.parseDouble(fanRating);
						Movie newMovie = new Movie(movieTitle, realYear, filmRating, realRating, duration, cast, genre, description);
						System.out.println(newMovie.toString());
						allMovies.add(newMovie);
						sc.close();
						duration = "";
						cast = new ArrayList<>();
					}
					
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	private static boolean isfilmRating(String rating) {
		final int numHours = 7;
		for (int i = 0; i < numHours; i++) {
			if (rating.equals(i + "h")) {
				return false;
			}
		}
		return true;
	}
}


