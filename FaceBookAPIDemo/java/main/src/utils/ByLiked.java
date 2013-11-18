package utils;

import java.util.List;
import java.util.TreeMap;

import managers.FacebookManager;

public class ByLiked implements TopBy {
	@Override
	public void doGet(TreeMap<String, Integer> treeMap, String gId, String from, String to) {
		// TODO Auto-generated method stub
		FacebookManager facebookManager = new FacebookManager();
		List<models.Post> posts = facebookManager.getPostsFQL(gId, from, to);
		for (models.Post post : posts) {
			String userId = post.actor_id;
			int count = post.likes.findValue("count").asInt();
			if (!treeMap.containsKey(userId)) {
				treeMap.put(userId, count);
			} else {
				int value = (int) treeMap.get(userId) + count;
				treeMap.put(userId, value);
			}
		}

		List<models.Comment> comments = facebookManager.getPostsCommentsFQL(
				gId, from, to);
		for (models.Comment comment : comments) {
			String userId = comment.fromid;
			int count = comment.likes;
			if (!treeMap.containsKey(userId)) {
				treeMap.put(userId, count);
			} else {
				int value = (int) treeMap.get(userId) + count;
				treeMap.put(userId, value);
			}
		}
	}
}
