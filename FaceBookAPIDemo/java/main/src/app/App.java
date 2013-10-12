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
				.setOAuthAppId("163257810547958")
				.setOAuthAppSecret("17588d6f705be66d58d29a73e3308515")
				.setOAuthAccessToken(
						"CAACUe2vN9PYBAPNtRs49sZC2bfXrRhGvk1bvpiHzsrsmHdGZBxNNEVOZATJHjSMZCxNREjME4JZAZCjQ6JMaROclM0diUmJShhuX6nt4tn4XtJpjkCpqxoghrngaJ2FWVMfVd5koD1dGRgHp2i818CjSWxeGvCdpZAEUHofDZCG7Ix42YX0XLKxVeRp5Yd12gZAoZD")
				.setOAuthPermissions("email,publish_stream,read_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();

		try {
			// facebook.postStatusMessage("Test post status!");
			// System.out.println("OK");
			ResponseList<Post> feed = facebook.getHome();
			
			for (Post post : feed) {
				System.out.println("Message: " + post.getMessage());
			}

			// Post p = feed.get(0);
			// System.out.println(p.getComments());
			//
			// PagableList<Comment> l = p.getComments();
			//
			// for (Comment comment : l) {
			// System.out.println(comment.getMessage());
			// }

			// System.out.println(Tools.toJson(p));
			// System.out.println(feed.size());
			//int max = 0;
			//Post res = null;
			

			//System.out.println(feed.get(0).toString());

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
