package models;

import java.net.URL;
import java.util.Date;

import facebook4j.Category;
import facebook4j.Comment;
import facebook4j.Like;
import facebook4j.PagableList;

public class Status {
	public String id;
	public String type;
	public String message;
	public URL url;
	public PagableList<Like> likes;
	public PagableList<Comment> comments;
	public Date createdTime;
	public Category from;
}
