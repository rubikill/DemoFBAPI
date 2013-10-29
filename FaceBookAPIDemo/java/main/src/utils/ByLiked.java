package utils;

import java.util.HashMap;
import java.util.List;

import facebook4j.Comment;
import facebook4j.Post;
import facebook4j.ResponseList;

public class ByLiked implements TopBy{

	@Override
	public void doGet(ResponseList<Post> posts, HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		//Duyet tung post
		for (Post post : posts) {
			//Xet user hien tai co trong map chua
			String userId = post.getFrom().getId();
			if (!map.containsKey(userId)) {
				//chua thi put vo voi so luot like
				map.put(userId, post.getLikes().size());
			} else {
				//co roi thi tang so luot like
				int value = map.get(userId) + post.getLikes().size();
				map.put(userId, value);
			}
			
			List<Comment> comments = post.getComments();
			
			for (Comment comment : comments) {
				String uId = comment.getFrom().getId();
				if (!map.containsKey(uId)) {
					map.put(uId, comment.getLikeCount());
				} else {
					int value = map.get(uId) + comment.getLikeCount();
					map.put(uId, value);
				}
			}
		}
	}
}
