package managers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.colorfulsoftware.rss.Attribute;
import com.colorfulsoftware.rss.Channel;
import com.colorfulsoftware.rss.Description;
import com.colorfulsoftware.rss.Extension;
import com.colorfulsoftware.rss.Item;
import com.colorfulsoftware.rss.Link;
import com.colorfulsoftware.rss.PubDate;
import com.colorfulsoftware.rss.RSS;
import com.colorfulsoftware.rss.Title;

import dao.IRSSHelper;

public class RSSManagerImpl implements IRSSManager {

	private IRSSHelper helper;

	public IRSSHelper getHelper() {
		return helper;
	}

	public void setHelper(IRSSHelper helper) {
		this.helper = helper;
	}

	public RSSManagerImpl(IRSSHelper helper) {
		// TODO Auto-generated constructor stub
		this.helper = helper;
	}

	public RSSManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String rssToString(RSS rss) {
		// TODO Auto-generated method stub
		return helper.rssToString(rss);
	}

	@Override
	public RSS createRSS(Channel channel, List<Attribute> attributes,
			List<Extension> extensions) {
		// TODO Auto-generated method stub
		return helper.createRSS(channel, attributes, extensions);
	}

	@Override
	public Channel createChanel(Title title, Link link,
			Description description, PubDate pubDate, List<Item> items) {
		// TODO Auto-generated method stub
		return helper.createChanel(title, link, description, pubDate, items);
	}

	@Override
	public Item createItem(Title title, Description description, PubDate pubDate) {
		// TODO Auto-generated method stub
		return helper.createItem(title, description, pubDate);
	}

	@Override
	public RSS createRSSFromFIT(String id, String tableId) {
		// TODO Auto-generated method stub
		Document doc;
		try {
			// need http protocol
			String link = "http://www.fit.hcmus.edu.vn/vn/Default.aspx?tabid="
					+ id;
			doc = Jsoup.connect(link).get();

			// get page title
			String title = doc.title();
			System.out.println("title : " + title);

			// get all links
			Elements element = doc.select("table[id=dnn_ctr" + tableId
					+ "_ViewNews_ucShowPost_tblShowListOne]");

			Elements tables = element.select("table");

			tables.remove(0);

			List<Item> items = new ArrayList<Item>();
			for (Element table : tables) {
				try {
					Title tit = createTitle(table.select("a").text());
					SimpleDateFormat format = new SimpleDateFormat(
							"(dd/MM/yyyy)", Locale.US);
					format.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date date = format.parse(table.select("span").text());
					PubDate pubDate = createPubDate(date.toString());

					Item item = createItem(tit, null, pubDate);
					items.add(item);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Channel channel = createChanel(createTitle(title), createLink(link),
					createDescription("RSS from FIT"), null, items);

			Attribute rssVersion = createAttribute("version", "2.0");
			Attribute  xmlns_slash = createAttribute("xmlns:slash", "http://purl.org/rss/1.0/modules/slash/");
			List<Attribute> attributes = new ArrayList<Attribute>();
			attributes.add(rssVersion);
			attributes.add(xmlns_slash);

			RSS rss = createRSS(channel, attributes, null);

			//System.out.println(rssToString(rss));
			
			return rss;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PubDate createPubDate(String date) {
		// TODO Auto-generated method stub
		return helper.createPubDate(date);
	}

	@Override
	public Title createTitle(String title) {
		// TODO Auto-generated method stub
		return helper.createTitle(title);
	}

	@Override
	public Description createDescription(String description) {
		// TODO Auto-generated method stub
		return helper.createDescription(description);
	}

	@Override
	public Attribute createAttribute(String name, String value) {
		// TODO Auto-generated method stub
		return helper.createAttribute(name, value);
	}

	@Override
	public Link createLink(String link) {
		// TODO Auto-generated method stub
		return helper.createLink(link);
	}

}
