package controllers;

import managers.FacebookManager;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import utils.Global;
import utils.Tools;

public class Application extends Controller {
  
    public static Result index() {
        return ok("Hello");
    }
    
    public static Result getStatus(){
    	JsonNode json = request().body().asJson();
    	System.out.println(json);
    	System.out.println("-------------------------------");

    	return ok(Tools.listToJson((new FacebookManager()).getFeeds()));
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