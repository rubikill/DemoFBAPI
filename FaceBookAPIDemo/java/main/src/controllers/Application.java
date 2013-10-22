package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import managers.FacebookManager;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import utils.Global;
import utils.Tools;
import facebook4j.Admin;
import facebook4j.Album;
import facebook4j.Group;
import facebook4j.GroupMember;
import facebook4j.Media;
import facebook4j.Photo;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;

public class Application extends Controller {

	public static Result index() {
		return ok("Hello");
	}
	/*************************************************************
	 * 
	 * 							Feed
	 * 
	 *************************************************************/
	public static Result getFeed() {

		FacebookManager facebookManager = new FacebookManager();
		
		System.out.println(facebookManager.toString());
		List<models.Status> s = new ArrayList<models.Status>();

		for (Post post : facebookManager.getFeeds()) {
			models.Status temp = new models.Status();
			temp.message = post.getMessage();
			temp.type = post.getType();
			temp.url = post.getPicture();
			temp.name = post.getFrom().getName();
			temp.id = post.getId();
			temp.comments = post.getComments();
			
			temp.likes = post.getLikes();
			
			s.add(temp);
		}

		return ok(Tools.listToJson(s));
	}

	public static Result getStatus() {
		return ok();
	}
	
	/*************************************************************
	 * 
	 * 							Album
	 * 
	 *************************************************************/
	/**
	 * 
	 * @return
	 */
	public static Result getAlbums() {
		System.out.println("get album called");
		FacebookManager facebookManager = new FacebookManager();
		List<Album> list =  facebookManager.getAlbums();
		System.out.println(list.size());
		return ok(Tools.listToJson(list));
	}
	
	public static Result getAlbum(String id) {
		System.out.println("-------single album--------");
		FacebookManager facebookManager = new FacebookManager();
		List<Photo> photos = facebookManager.getPhotos(id);
		return ok(Tools.listToJson(photos));
	}

	
	/*************************************************************
	 * 
	 * 							Status
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

	public static Result postPhoto(String s) {
		FacebookManager facebookManager = new FacebookManager();
		List<FilePart> fileParts = request().body().asMultipartFormData()
				.getFiles();
		for (FilePart filePart : fileParts) {
			facebookManager.postPhoto(new Media(filePart.getFile()));
		}

		System.out.println("OK");
		return ok("OK men!");
	}
	
	public static Result getPhoto(String id) {
		FacebookManager facebookManager = new FacebookManager();
		
		facebookManager.getPhoto(id);
		return ok();
	}

	public static Result setToken(String token) {
		Global.OAuthAccessToken = token;
		return ok();
	}
	
	/*************************************************************
	 * 
	 * 							Like
	 * 
	 *************************************************************/
	public static Result like(String id){
		System.out.println("Like is called");
		(new FacebookManager()).like(id);
		return ok("");
	}
	
	public static Result unLike(String id){
		System.out.println("Unlike is called");
		(new FacebookManager()).unLike(id);
		return ok("");
	}
	
	/*************************************************************
	 * 
	 * 							User
	 * 
	 *************************************************************/
	public static Result getUser(String id){
		System.out.println(id);
		User u = (new FacebookManager()).getUser(id);
		
		System.out.println(u.toString());
		return ok(Tools.toJson(u));
	}
	
	
	/*************************************************************
	 * 
	 * 							Page
	 * 
	 *************************************************************/
	public static Result getPages(){
		FacebookManager facebookManager = new FacebookManager();
		List<Admin> list = facebookManager.getPages();
		if (list == null) {
			return badRequest("NULL");
		}		
		return ok(Tools.listToJson(list));
	}
	
	/*************************************************************
	 * 
	 * 							Page
	 * 
	 *************************************************************/
	public static Result getGroups(){
		FacebookManager facebookManager = new FacebookManager();
		List<Group> list = facebookManager.getGroups();
				
		return ok(Tools.listToJson(list));
	}
	
	public static Result getGroup(String id){
		FacebookManager facebookManager = new FacebookManager();
		Group group = facebookManager.getGroup(id);
		
		System.out.println(group.getOwner());
		System.out.println(group.getVersion());
		System.out.println(group.getVenue());
		System.out.println(group.getDescription());
				
		return ok(Tools.toJson(group));
	}
	
	public static Result getGroupMember(String id){
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<GroupMember> members = facebookManager.getGroupMembers(id);			
		return ok(Tools.listToJson(members));
	}
	
	public static Result getGroupFeed(String id){
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<Post> posts = facebookManager.getGroupFeed(id);
		
		List<models.Status> s = new ArrayList<models.Status>();

		for (Post post : posts) {
			models.Status temp = new models.Status();
			temp.message = post.getMessage();
			temp.type = post.getType();
			temp.url = post.getPicture();
			temp.name = post.getFrom().getName();
			temp.id = post.getId();
			temp.comments = post.getComments();
			
			temp.likes = post.getLikes();
			
			s.add(temp);
		}
		
		return ok(Tools.listToJson(s));
	}
	
	public static Result getGroupPictureURL(String id){
		FacebookManager facebookManager = new FacebookManager();
		URL group = facebookManager.getGroupPictureURL(id);			
		return ok(Tools.toJson(group));
	}
	
	
//	public static Result getPage(String id){
//		System.out.println(id);
//		Page p = (new FacebookManager()).getPage(id);
//		
//		System.out.println(p.toString());
//		return ok(Tools.toJson(p));
//	}
}