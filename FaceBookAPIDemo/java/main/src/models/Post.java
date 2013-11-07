package models;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import facebook4j.Like;

public class Post {
	//The ID of the post
	public String post_id;
	public String actor_id;
	public JsonNode likes;
	public String message;
	public List<Comment> comments;
}
