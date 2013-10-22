package models;

import java.net.URL;

import facebook4j.Comment;
import facebook4j.Like;
import facebook4j.PagableList;

public class Status {
	public String id;
	public String type;
	public String message;
	public String name;
	public URL url;
	public PagableList<Like> likes;
	public PagableList<Comment> comments;
}
