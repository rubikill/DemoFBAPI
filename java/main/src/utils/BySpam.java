package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import managers.FacebookManager;
import models.StatisticInfo;

public class BySpam {
	
	//Liked/(post + comment)
	public void doGet(TreeMap<String, Float> treeMap, String gId, String from, String to) {
		// TODO Auto-generated method stub
		FacebookManager facebookManager = new FacebookManager();
		List<models.Post> posts = facebookManager.getPostsFQL(gId, from, to);
		List<models.Comment> comments = facebookManager.getPostsCommentsFQL(
				gId, from, to);

		TreeMap<String, Integer> treePostComment = new TreeMap<String, Integer>();
		TreeMap<String, Integer> treeLiked = new TreeMap<String, Integer>();

		//Tong so post
		//For tung post put user
		for (models.Post post : posts) {
			String userId = post.actor_id;
			if (!treePostComment.containsKey(userId)) {
				treePostComment.put(userId, 1);
			} else {
				int value = (int) treePostComment.get(userId) + 1;
				treePostComment.put(userId, value);
			}

			int count = post.likes.findValue("count").asInt();
			if (!treeLiked.containsKey(userId)) {
				treeLiked.put(userId, count);
			} else {
				int value = (int) treeLiked.get(userId) + count;
				treeLiked.put(userId, value);
			}
		}

		for (models.Comment comment : comments) {
			String userId = comment.fromid;
			if (!treePostComment.containsKey(userId)) {
				treePostComment.put(userId, 1);
			} else {
				int value = (int) treePostComment.get(userId) + 1;
				treePostComment.put(userId, value);
			}

			int count = comment.likes;
			if (!treeLiked.containsKey(userId)) {
				treeLiked.put(userId, count);
			} else {
				int value = (int) treeLiked.get(userId) + count;
				treeLiked.put(userId, value);
			}
		}

		@SuppressWarnings("rawtypes")
		Iterator iter = treePostComment.entrySet().iterator();
		@SuppressWarnings("unused")
		List<StatisticInfo> res = new ArrayList<StatisticInfo>();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry mEntry = (Map.Entry) iter.next();
			String userId = (String)mEntry.getKey();
			if (treeLiked.containsKey(userId)) {
				float value = treeLiked.get(mEntry.getKey()).floatValue()
						/ Float.parseFloat(mEntry.getValue().toString());
				treeMap.put(mEntry.getKey().toString(), value);
			} else {
				treeMap.put(mEntry.getKey().toString(), (float) 0);
			}
		}
	}
	
	public static <T1, T2> void  printMap(Map<T1, T2> map) {
		for (@SuppressWarnings("rawtypes") Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}
	}
}
