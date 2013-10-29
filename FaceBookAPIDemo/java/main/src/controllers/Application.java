package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import utils.Global;

public class Application extends Controller {

	public static Result index() {
		return ok("Hello");
	}

	
	
	public static Result setToken(String token) {
		Global.OAuthAccessToken = token;
		return ok();
	}
}