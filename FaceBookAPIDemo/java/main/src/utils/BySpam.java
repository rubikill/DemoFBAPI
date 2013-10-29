package utils;

import java.util.HashMap;

import facebook4j.Post;
import facebook4j.ResponseList;

public class BySpam implements TopBy{

	@Override
	public void doGet(ResponseList<Post> posts, HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		for (Post post : posts) {
			String userId = post.getFrom().getId();
			if (!map.containsKey(userId)) {
				map.put(userId, 1);
			} else {
				int value = map.get(userId) + 1;
				map.put(userId, value);
			}
		}
	}
}
