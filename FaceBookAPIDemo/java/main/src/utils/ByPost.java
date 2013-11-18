package utils;

import java.util.List;
import java.util.TreeMap;

import managers.FacebookManager;

public class ByPost implements TopBy{

	@Override
	public void doGet(TreeMap<String, Integer> treeMap, String gId, String from,
			String to) {
		FacebookManager facebookManager = new FacebookManager();
		List<models.Post> posts = facebookManager.getPostsFQL(gId, from, to);
		
		// TODO Auto-generated method stub
		for (models.Post post : posts) {
			String userId = post.actor_id;
			if (!treeMap.containsKey(userId)) {
				treeMap.put(userId, 1);
			} else {
				int value = (int)treeMap.get(userId) + 1;
				treeMap.put(userId, value);
			}
		}
	}
}
