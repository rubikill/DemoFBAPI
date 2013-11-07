package utils;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import managers.FacebookManager;

import facebook4j.Post;
import facebook4j.ResponseList;

public class ByPost implements TopBy{

	@Override
	public void doGet(TreeMap treeMap, String gId, String from,
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
