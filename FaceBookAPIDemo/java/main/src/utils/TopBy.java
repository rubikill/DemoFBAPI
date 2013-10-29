package utils;

import java.util.HashMap;

import facebook4j.Post;
import facebook4j.ResponseList;

public interface TopBy {
	void doGet(ResponseList<Post> posts, HashMap<String, Integer> map);
}
