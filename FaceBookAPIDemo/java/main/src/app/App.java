package app;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAppId("380947045341754")
				.setOAuthAppSecret("223fa6f1ebc24108b556982b57531750")
				.setOAuthAccessToken(
						"CAAFaeCVQ2joBAL2eh0tShkqLjlgSWDT4JpZBV4JZCe5VCdpctbZACS3vjmuMvicqMXhii9lnr428iwYTsySvPxmwuCootEoAPRxKZCYhRZCx4lZBp6s9vJfiYzA7VPyGkt6jCZCGfP1xWe6IZCt6TReeQxnyQFt4VrSiBY1rW49PA4hInUN0YNA1fZAYolZC3ZCa2Q2JcffNTmnNQZDZD")
				.setOAuthPermissions("email,publish_stream,read_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();
		
		try {
			facebook.postStatusMessage("Test post status!");
			System.out.println("OK");
			//ResponseList<Post> feed = facebook.getHome();
			
			//System.out.println(feed.size());
//			for (Post post : feed) {
//				System.out.println("Description: " + post.getDescription());
//			}
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
