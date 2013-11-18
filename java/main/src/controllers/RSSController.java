package controllers;

import java.util.List;

import com.colorfulsoftware.rss.Item;
import com.colorfulsoftware.rss.RSS;

import managers.IRSSManager;
import managers.RSSManagerImpl;
import dao.RSSHelperImpl;
import play.api.templates.Xml;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Global;
import utils.Tools;

public class RSSController extends Controller {

	public static Result getRssFromFIT(String id, String tableid) {
		IRSSManager manager = new RSSManagerImpl(new RSSHelperImpl());

		RSS rss = manager.createRSSFromFIT(Global.ThongTinCQId, Global.ThongTinCQTable);
		Xml x = new Xml(manager.rssToString(rss));
		return ok(x);
	}
	
	public static Result getRssFromFITJson(String id, String tableid) {
		IRSSManager manager = new RSSManagerImpl(new RSSHelperImpl());
		RSS rss = manager.createRSSFromFIT(Global.ThongTinCQId, Global.ThongTinCQTable);
		
		List<Item> list = rss.getChannel().getItems();
		return ok(Tools.listToJson(list));
	}
}
