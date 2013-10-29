package controllers;

import managers.FacebookManager;
import facebook4j.User;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Tools;

public class UserController extends Controller{
	/*************************************************************
	 * 
	 * User
	 * 
	 *************************************************************/
	public static Result getUser(String id) {
		System.out.println(id);
		User u = (new FacebookManager()).getUser(id);

		System.out.println(u.toString());
		return ok(Tools.toJson(u));
	}
}
