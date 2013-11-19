package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.colorfulsoftware.rss.Attribute;
import com.colorfulsoftware.rss.Channel;
import com.colorfulsoftware.rss.Description;
import com.colorfulsoftware.rss.Extension;
import com.colorfulsoftware.rss.Item;
import com.colorfulsoftware.rss.Link;
import com.colorfulsoftware.rss.PubDate;
import com.colorfulsoftware.rss.RSS;
import com.colorfulsoftware.rss.RSSDoc;
import com.colorfulsoftware.rss.Title;

public class App4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			RSSDoc rssDoc = new RSSDoc();

			// make sure title or description is present for item
			Title title = rssDoc.buildTitle("my title");
			Description description = rssDoc.buildDescription("my description");
			Item item = rssDoc.buildItem(title, null, description, null, null,
					null, null, null, null, null, null);

			// title, link and description are required for channel
			// we add pubDate and items because these are usually there
			Link link = rssDoc
					.buildLink("http://www.colorfulsoftware.com/projects/rsspect");
			List<Item> items = new ArrayList<Item>();
			PubDate pubDate = rssDoc.buildPubDate(Calendar.getInstance()
					.getTime().toString());
			items.add(item);
			Channel channel = rssDoc.buildChannel(title, link, description,
					null, null, null, null, pubDate, null, null, null, null,
					null, null, null, null, null, null, null, null, items);

			// rss element require channel and version attribue
			Attribute rssVersion = rssDoc.buildAttribute("version", "2.0");
			List<Attribute> attributes = new ArrayList<Attribute>();
			attributes.add(rssVersion);
			
			RSS rss = rssDoc.buildRSS(channel, attributes, null);
			
			
			String myRssStr = new RSSDoc().readRSSToString(rss, "javanet.staxutils.IndentingXMLStreamWriter");
			
			
			System.out.println(myRssStr);

		} catch (Exception e) {
			System.out.println("error with feed: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
