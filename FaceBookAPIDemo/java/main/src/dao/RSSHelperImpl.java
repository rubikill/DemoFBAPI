package dao;

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
import com.colorfulsoftware.rss.RSSpectException;
import com.colorfulsoftware.rss.Title;

public class RSSHelperImpl implements IRSSHelper {

	private RSSDoc rssDoc;

	public RSSHelperImpl() {
		// TODO Auto-generated constructor stub
		try {
			rssDoc = new RSSDoc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String rssToString(RSS rss) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.readRSSToString(rss,
					"javanet.staxutils.IndentingXMLStreamWriter");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RSS createRSS(Channel channel, List<Attribute> attributes,
			List<Extension> extensions) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildRSS(channel, attributes, extensions);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Channel createChanel(Title title, Link link,
			Description description, PubDate pubDate, List<Item> items) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildChannel(title, link, description, null, null,
					null, null, pubDate, null, null, null, null, null, null,
					null, null, null, null, null, null, items);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Item createItem(Title title, Description description, PubDate pubDate) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildItem(title, null, description, null, null, null,
					null, null, pubDate, null, null);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PubDate createPubDate(String date) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildPubDate(date);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Title createTitle(String title) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildTitle(title);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Description createDescription(String description) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildDescription(description);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Attribute createAttribute(String name, String value) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildAttribute(name, value);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Link createLink(String link) {
		// TODO Auto-generated method stub
		try {
			return rssDoc.buildLink(link);
		} catch (RSSpectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
