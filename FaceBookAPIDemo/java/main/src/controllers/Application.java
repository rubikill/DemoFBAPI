package controllers;

import java.util.ArrayList;
import java.util.List;

import managers.FacebookManager;
import models.Status;

import org.codehaus.jackson.JsonNode;

import facebook4j.Comment;
import facebook4j.Post;

import play.mvc.Controller;
import play.mvc.Result;
import utils.Global;
import utils.Tools;

public class Application extends Controller {
  
    public static Result index() {
        return ok("Hello");
    }
    
    public static Result getFeed(){
    	
    	List<models.Status> s = new ArrayList<models.Status>();
    	
    	for (Post post : (new FacebookManager()).getFeeds()) {
    		models.Status temp = new models.Status();
    		temp.message = post.getMessage();
    		temp.type = post.getType();
    		temp.url = post.getPicture();
    		temp.name = post.getFrom().getName();
    		
    		s.add(temp);
		}
    	
    	return ok(Tools.listToJson(s));
    }
    
    public static Result getStatus(){
    	return ok();
    }
    
    public static Result postStatus(String s){
    	System.out.println(s);
    	System.out.println("-------------------------------------");
    	//System.out.println(Global.OAuthAccessToken);
    	try {
    		(new FacebookManager()).postStatus(s);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	System.out.println("OK");
    	return ok("");
    }
    
    public static Result setToken(String token){
    	
//    	/System.out.println(token);
    	
    	Global.OAuthAccessToken = token;
    	return ok();
    }
}