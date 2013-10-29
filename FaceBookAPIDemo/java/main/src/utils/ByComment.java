package utils;

import java.util.HashMap;
import java.util.List;

import facebook4j.Comment;
import facebook4j.Post;
import facebook4j.ResponseList;

public class ByComment implements TopBy{

	@Override
	public void doGet(ResponseList<Post> posts, HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		for (Post post : posts) {			
			List<Comment> comments = post.getComments();
			for (Comment comment : comments) {
				String userId = comment.getFrom().getId();
				if (!map.containsKey(userId)) {
					map.put(userId, 1);
				} else {
					int value = map.get(userId) + 1;
					map.put(userId, value);
				}
			}
		}
	}
}
