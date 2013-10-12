package managers;

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
		cb.setDebugEnabled(true)
				.setOAuthAppId("380947045341754")
				.setOAuthAppSecret("254eb1b852604f862034e642746b4e6a")
				.setOAuthAccessToken(
						"CAAFaeCVQ2joBAFTIdNXKqArdezi6BZB4qj9JIODZB8N0oBQZBRp5YfTbtfGeemeXy4GOoO3HlI50DgeoGNQBLrRyYCfO6bpspInaUeXzSJqOtApO070N9zMYRSkCLpIBFY4Jj4xZCzZCS7FPfzZB4XSdXtMuaNiJGuQzZAfpJXGZBJCHwdzk2TWTIITTxhOiWNGfam2dNq0vaQZDZD")
				.setOAuthPermissions("email,publish_stream,read_stream,...");
		
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
	}
	
	public boolean postStatus(String status){
		try {
			facebook.postStatusMessage(status);
			return true;
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ResponseList<Post> getFeeds(){
		ResponseList<Post> feed = null;
		try {
			feed = facebook.getHome();
			//System.out.println(feed.get(0).toString());
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return feed;
	}
}
