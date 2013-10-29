package controllers;

import java.util.List;

import managers.FacebookManager;
import facebook4j.Admin;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Tools;

public class PageController extends Controller{
	/*************************************************************
	 * 
	 * Page
	 * 
	 *************************************************************/
	public static Result getPages() {
		FacebookManager facebookManager = new FacebookManager();
		List<Admin> list = facebookManager.getPages();
		if (list == null) {
			return badRequest("NULL");
		}
		return ok(Tools.listToJson(list));
	}
}
