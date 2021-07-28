package search.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application {

	public static void main(String[] args) throws IOException {

		System.out.println("Please choose an option from the list below");
		System.out.println("Choose 1 : Search a url");
		System.out.println("Choose 2 : Delete a url from cache");
		System.out.println("Choose 3 : Delete cache");
		System.out.println("Choose 4 : Rank the web pages according to the occurance of a word");

//		String url = "https://www.shiksha.com/";
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your choice");
		int choice = sc.nextInt();
		sc = new Scanner(System.in);

		switch (choice) {
		case 0:
			System.out.println("please select a valid number");
			break;

		case 1:
			System.out.println("Please enter the url to be searched");
			String url = sc.nextLine();
			if (isValidUrl(url)) {
				if (!Cache.isAvailable(url)) {
					Cache.addcache(url);
					Crawler.crawlWeb(1, url, new ArrayList<String>());

				} else {
					System.out.println("This URL has already been crawled.");
				}

			} else {
				System.out.println("Please enter a valid url");
			}

			break;  

		case 2:
			System.out.println("Please enter a url to be deleted");
			url = sc.nextLine();
			if (isValidUrl(url)) {
				if (Cache.isAvailable(url)) {
					Cache.deleteCache(url);
					if (!Cache.isAvailable(url)) {
						System.out.println("URL successfully deleted from Cache");
					}
				} else {
					System.out.println("URL is not cached");
				}
			} else {
				System.out.println("Please enter a valid URL");
			}
			break;

		case 3:
			System.out.println("Write code..");
			break;

		case 4:
			System.out.println("Please enter a word to be searched");
			FindWord.readAllFiles();
			break;

		default:
			System.out.println("Please select a valid number");
			break;
		}

	}

	private static boolean isValidUrl(String url) {
		if (Pattern.matches(Crawler.URL_PATTERN, url))
			return true;
		return false;
	}

}
