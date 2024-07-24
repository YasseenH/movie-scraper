import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
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
	
	public static void main(String[] args) throws FileNotFoundException {
		//Insert File name here (Removed to prevent rewriting)
		PrintWriter output = new PrintWriter(new File("FILENAME.csv"));
		for (int i = 0; i < LINKS.length; i++) {
			try {
				final Document mainDoc = Jsoup.connect(LINKS[i]).timeout(20000).get();
				System.out.println("Connected!");
				Elements movies = mainDoc.getElementsByClass("ipc-metadata-list-summary-item sc-10233bc-0 iherUv cli-parent");
				
				//Goes through all the movies on the page
				for (Element movieBlock : movies) {
					//Grabs the link to the indiviual movie and connects
					String link = "https://www.imdb.com" + movieBlock.getElementsByClass("ipc-title-link-wrapper").attr("href");
					Document subDoc = Jsoup.connect(link).get();
					
					//Scrapes the title
					movieTitle = subDoc.getElementsByClass("hero__primary-text").text();

					//Scrapes the rating
					fanRating = subDoc.getElementsByClass("sc-eb51e184-1 cxhhrI").first().text();

					//Scrapes the description
					description = subDoc.getElementsByClass("sc-2d37a7c7-2 PeLXr").text();
					
					//Scrapes all the genre elements and gets all if there are multiple
					Elements genres = subDoc.getElementsByClass("ipc-chip-list__scroller");
					StringBuilder genreBuilder = new StringBuilder();
					for (Element e : genres) {
						String genreText = e.getElementsByTag("a").text();
						if (genreBuilder.length() > 0) {
							genreBuilder.append(" ");
						}
						genreBuilder.append(genreText);
					}
					genre = genreBuilder.toString();

					//Scrapes all the relevant cast members
					Elements allCastMembers = subDoc.getElementsByClass("ipc-sub-grid ipc-sub-grid--page-span-2 ipc-sub-grid--wraps-at-above-l ipc-shoveler__grid");
					
					//Scrapes all the members of the cast
					Elements allCast = allCastMembers.first().getElementsByTag("a");
					for (int j = 1; j < allCast.size(); j+=3) {
						cast.add(allCast.get(j).text());
					}

					//Grabs the Class that holds the film rating, year released, and duration
					Elements tempScope = subDoc.getElementsByClass("ipc-inline-list ipc-inline-list--show-dividers sc-d8941411-2 cdJsTz baseAlt");
					for (Element e : tempScope) {
						//Raw Data
						String movieDetails = e.tagName("li").text();
						Scanner sc = new Scanner(movieDetails);
						year = sc.next();
						String temp = sc.next();

						//Makes sure that the next element is a rating
						if (isfilmRating(temp)) {
							filmRating = temp;
						} else {
							filmRating = "None";
							duration = temp;
						}
						
						//Handles edge case of Not Rated
						if (filmRating.equals("Not")) {
							filmRating += " " + sc.next();
						}

						//Adds the full duration
						while(sc.hasNext()) {
							duration += " " + sc.next();
						}
						
						output.write(movieTitle + "," + year + "," + fanRating + "," + filmRating
						+ "," + genre + ",\"" + description + "\",\"" + getCast(cast) + "\"," + duration + "\n");
						sc.close();
						duration = "";
						cast = new ArrayList<>();
					}
					
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Finshed Scraping!");
		}
		output.close();
	}
	
	//Returns a String of the cast that uses ',' to seperate names
	private static String getCast(ArrayList<String> list) {
		StringBuilder result = new StringBuilder();
		result.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			result.append(",");
			result.append(list.get(i));
		}
		return result.toString();
	}

	//Checks to make sure the String is a rating
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


