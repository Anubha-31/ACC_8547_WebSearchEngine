package search.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	private static final int MAX_DEPTH = 2;
	private static final String URL_PATTERN = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}([-a-zA-Z0-9@:%._\\+~#?&//=]*)";

	public static void main(String[] args) throws IOException {
		
		String url="https://www.shiksha.com/";
		Boolean flag=Cache.isAvailable(url);
		if (flag==false) {
		Cache.Addcache(url);
		crawlWeb(1, url, new ArrayList<String>());
		
		}
		else {
		System.out.println("This URL has already been crawled.");
		}

	}

	private static void crawlWeb(int depth, String url, ArrayList<String> visitedURL) {
		if (!isEmpty(url) && depth <= MAX_DEPTH) {
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

	}

	private static boolean isEmpty(String url) {
		if (url != null && url != "" && Pattern.matches(URL_PATTERN, url))
			return false;
		return true;
	}

	private static Document getDocument(String url, ArrayList<String> visitedURL) {
		TextToHTML html = new TextToHTML();
		Connection conn = Jsoup.connect(url);
		
		try {
			Document doc = conn.ignoreContentType(true).get();
			if (conn.response().statusCode() == 200) {
				System.out.println("Link " + url);
				html.testfiles(url);
				visitedURL.add(url);
				return doc;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}


}
