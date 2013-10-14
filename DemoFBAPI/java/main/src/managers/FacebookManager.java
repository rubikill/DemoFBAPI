package managers;

import java.util.ArrayList;
import java.util.List;

import utils.Global;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookManager {

	public Facebook facebook;

	public FacebookManager() {
		// TODO Auto-generated constructor stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthAppId(Global.OAuthAppId)
				.setOAuthAppSecret(Global.OAuthAppSecret)
				.setOAuthAccessToken(Global.OAuthAccessToken)
				.setOAuthPermissions("email,publish_stream,read_stream,user_status,user_likes,publish_actions...");

		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
		
	}

	public boolean postStatus(String status) {
		if (Global.OAuthAccessToken != null) {
			try {
				facebook.postStatusMessage(status);
				return true;
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}

		}
		return false;
	}

	public ResponseList<Post> getFeeds() {
		if (Global.OAuthAccessToken != null) {
			//System.out.println("--------------------get feed---------");
			try {
				ResponseList<Post> list = facebook.getHome(new Reading().limit(5));
//				System.out.println("--------------------1---------");
//				System.out.println(list.size());
//				//List<String> res = new ArrayList<String>();
//				for (Post post : list) {
//					//res.add(post.getMessage());
//					System.out.println(post.getCaption());
//					System.out.println(post.getMessage());
//					System.out.println(post.getLikes());
//				} 
				
				return list;
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public List<String> getStatus() {
		if (Global.OAuthAccessToken != null) {
			try {
				ResponseList<Post> list = facebook.getStatuses((new Reading().limit(3)));
				List<String> res = new ArrayList<String>();
				for (Post post : list) {
					res.add(post.getMessage());
					System.out.println(post.getCaption());
					System.out.println(post.getMessage());
					System.out.println(post.getLikes());
				} 
				
				return res;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
}