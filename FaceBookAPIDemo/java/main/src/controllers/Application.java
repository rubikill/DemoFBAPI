package controllers;

import managers.FacebookManager;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
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
    	(new FacebookManager()).postStatus(s);
    	
    	System.out.println("OK");
    	return ok("");
    }
}
