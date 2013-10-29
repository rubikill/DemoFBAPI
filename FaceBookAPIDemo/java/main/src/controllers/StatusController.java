package controllers;

import managers.FacebookManager;
import play.mvc.Controller;
import play.mvc.Result;

public class StatusController extends Controller {
	/*************************************************************
	 * 
	 * Status
	 * 
	 *************************************************************/
	public static Result postStatus(String s) {
		System.out.println(s);
		System.out.println("-------------------------------------");
		// System.out.println(Global.OAuthAccessToken);
		try {
			(new FacebookManager()).postStatus(s);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("OK");
		return ok("");
	}
	
	public static Result getStatus() {
		return ok();
	}
}
