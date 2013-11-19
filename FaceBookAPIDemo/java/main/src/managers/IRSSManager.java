package managers;

import java.util.List;

import com.colorfulsoftware.rss.Attribute;
import com.colorfulsoftware.rss.Channel;
import com.colorfulsoftware.rss.Description;
import com.colorfulsoftware.rss.Extension;
import com.colorfulsoftware.rss.Item;
import com.colorfulsoftware.rss.Link;
import com.colorfulsoftware.rss.PubDate;
import com.colorfulsoftware.rss.RSS;
import com.colorfulsoftware.rss.Title;

public interface IRSSManager {
	String rssToString(RSS rss);
	RSS createRSS(Channel channel, List<Attribute> attributes, List<Extension> extensions);
	Channel createChanel(Title title, Link link, Description description, PubDate pubDate, List<Item> items);
	Item createItem(Title title, Description description, PubDate pubDate);
	RSS createRSSFromFIT(String id, String tableId);
	PubDate createPubDate(String date);
	Title createTitle(String title);
	Description createDescription(String description);
	Attribute createAttribute(String name, String value);
	Link createLink(String link);
}
