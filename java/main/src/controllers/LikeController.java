package controllers;

import managers.FacebookManager;
import play.mvc.Controller;
import play.mvc.Result;

public class LikeController extends Controller{
	/*************************************************************
	 * 
	 * Like
	 * 
	 *************************************************************/
	public static Result like(String id) {
		System.out.println("Like is called");
		(new FacebookManager()).like(id);
		return ok("");
	}

	public static Result unLike(String id) {
		System.out.println("Unlike is called");
		(new FacebookManager()).unLike(id);
		return ok("");
	}
}
