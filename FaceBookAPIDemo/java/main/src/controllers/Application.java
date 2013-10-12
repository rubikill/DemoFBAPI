package controllers;

import managers.FacebookManager;

import org.codehaus.jackson.JsonNode;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

import play.*;
import play.mvc.*;

import utils.Tools;
import views.html.*;

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
