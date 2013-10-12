package managers;

import utils.Global;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
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
				.setOAuthPermissions("email,publish_stream,read_stream,...");

		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
	}

	public boolean postStatus(String status) {
		if (Global.OAuthAccessToken != null) {
			try {
				facebook.postStatusMessage(status);
				System.out.println("access token" + facebook.getOAuthAccessToken());
				System.out.println("app" + facebook.getOAuthAppAccessToken());
				return true;
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public ResponseList<Post> getFeeds() {
		ResponseList<Post> feed = null;

		if (Global.OAuthAccessToken != null) {
			try {
				feed = facebook.getHome();
				// System.out.println(feed.get(0).toString());
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return feed;
	}
}
