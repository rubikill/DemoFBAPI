package app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc;
		try {

			// need http protocol
			doc = Jsoup.connect("http://www.fit.hcmus.edu.vn/vn/Default.aspx?tabid=260").get();

			//System.out.println(doc);
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);

			// get all links
			Elements element = doc.select("table[id=dnn_ctr654_ViewNews_ucShowPost_tblShowListOne]");
			
//			System.out.println(element);
			
			Elements tables = element.select("table");
			
			tables.remove(0);
//			
			//System.out.println(tables);
//			//System.out.println(element);
			for (Element table : tables) {

				//System.out.println(table);
				// get the value from href attribute
				System.out.println("\ntitle : " + table.select("a").text());
				//System.out.println("link : " + table.select("a").attr("href"));
				System.out.println("date : " + table.select("span").text());
				//System.out.println("post_title : " + table.select("td[class=post_title"));
				//System.out.println("title : " + table.select("td[class=post_title").select("a").attr("title"));
//				System.out.println("text : " + table.text());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
