package app;

import java.util.Date;

import org.joda.time.DateTime;

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

		// ConfigurationBuilder cb = new ConfigurationBuilder();
		//
		// cb.setDebugEnabled(true)
		// .setOAuthAppId("163257810547958")
		// .setOAuthAppSecret("17588d6f705be66d58d29a73e3308515")
		// .setOAuthAccessToken(
		// "CAACUe2vN9PYBAPNtRs49sZC2bfXrRhGvk1bvpiHzsrsmHdGZBxNNEVOZATJHjSMZCxNREjME4JZAZCjQ6JMaROclM0diUmJShhuX6nt4tn4XtJpjkCpqxoghrngaJ2FWVMfVd5koD1dGRgHp2i818CjSWxeGvCdpZAEUHofDZCG7Ix42YX0XLKxVeRp5Yd12gZAoZD")
		// .setOAuthPermissions("email,publish_stream,read_stream,...");
		// FacebookFactory ff = new FacebookFactory(cb.build());
		// Facebook facebook = ff.getInstance();
		//
		// ResponseList<Post> feed;
		// try {
		// feed = facebook.getHome();
		// for (Post post : feed) {
		// System.out.println("Message: " + post.getMessage());
		// }
		// } catch (FacebookException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

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
		// int max = 0;
		// Post res = null;

		// System.out.println(feed.get(0).toString());

		// } catch (FacebookException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		//
		//Calendar lCDateTime = Calendar.getInstance();
		
		//System.out.println(new Date(2013, 9, 11).toString());
		DateTime d = new DateTime(2013, 9, 11, 0, 0);
		
		System.out.println(d.getMillis());
		
		System.out.println(new Date(1378882800000L).toString());
		
	}

}
