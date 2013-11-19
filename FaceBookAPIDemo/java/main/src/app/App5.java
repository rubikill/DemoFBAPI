package app;

import utils.Global;
import dao.RSSHelperImpl;
import managers.IRSSManager;
import managers.RSSManagerImpl;

public class App5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IRSSManager manager = new RSSManagerImpl(new RSSHelperImpl());

		System.out.println(manager.rssToString(manager.createRSSFromFIT(
				Global.ThongTinCQId, Global.ThongTinCQTable)));
	}

}
