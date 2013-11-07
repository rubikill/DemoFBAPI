package utils;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import managers.FacebookManager;

import facebook4j.Comment;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.internal.org.json.JSONArray;

public class ByComment implements TopBy {

	@Override
	public void doGet(TreeMap treeMap, String pId, String from,
			String to) {
		// TODO Auto-generated method stub
		FacebookManager facebookManager = new FacebookManager();
		List<models.Comment> comments = facebookManager.getPostsCommentsFQL(pId, from, to);
		
		for (models.Comment comment : comments) {
			String userId = comment.fromid;
			if (!treeMap.containsKey(userId)) {
				treeMap.put(userId, 1);
			} else {
				int value = (int)treeMap.get(userId) + 1;
				treeMap.put(userId, value);
			}
		}
	}
}
