package search.web;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	private static final int MAX_DEPTH = 2;

	public static void main(String[] args) {
		 crawlWeb(1, "https://en.wikipedia.org/", new ArrayList<String>());

	}

	private static void crawlWeb(int depth, String url, ArrayList<String> visitedURL) {
		if (/* !visitedURL.contains(url) && */ depth <= MAX_DEPTH) {
			System.out.println("Level: "+ depth);
			Document doc = getDocument(url, visitedURL);
			if (doc != null) {
				Elements elements = doc.select("a[href]");
				for (Element element : elements) {
					String linkonPage = element.absUrl("href");
					if (!visitedURL.contains(linkonPage)) {
						crawlWeb(depth++, linkonPage, visitedURL);
					}
				}
			}
		}
		//System.out.println("Anubha");
	}

	private static Document getDocument(String url, ArrayList<String> visitedURL) {

		Connection conn = Jsoup.connect(url);
		try {
			Document doc = conn.ignoreContentType(true).get();
			if (conn.response().statusCode() == 200) {
				System.out.println("Link " + url);
				visitedURL.add(url);
				return doc;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

}
