package controllers;

import java.util.ArrayList;
import java.util.List;

import managers.FacebookManager;
import facebook4j.Post;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Tools;

public class FeedController extends Controller {
	/*************************************************************
	 * 
	 * Feed
	 * 
	 *************************************************************/
	public static Result getFeed() {

		FacebookManager facebookManager = new FacebookManager();

		System.out.println(facebookManager.toString());
		List<models.Status> s = new ArrayList<models.Status>();

		for (Post post : facebookManager.getFeeds()) {
			models.Status temp = new models.Status();
			temp.message = post.getMessage();
//			temp.type = post.getType();
//			temp.url = post.getPicture();
//			temp.id = post.getId();
//			temp.comments = post.getComments();

			//temp.likes = post.getLikes();

			s.add(temp);
		}

		return ok(Tools.listToJson(s));
	}
}
