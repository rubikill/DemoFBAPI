package app;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.h2.constant.SysProperties;

import utils.Tools;
import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.json.DataObjectFactory;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAppId("380947045341754")
				.setOAuthAppSecret("254eb1b852604f862034e642746b4e6a")
				.setOAuthAccessToken(
						"CAAFaeCVQ2joBALvh11uLCPh1SmL2c2sQcJ45pqCVbAR7xBA4fAtRgqNCDNzZCZABgjoLxyyZBn5BGKEt6BOfZARavMHE57YBwjpjvVJbhQJ3tiaHxvzZA2eBDOMMZBKFYS4ZCvo0JIE0ZAZBEzjeGz5ouLaDXVkdOj5tfLtZA9cVI9fIMZBZAxCMSAEKP5ig5sQtcDwcLZB5IyiZBy2QZDZD")
				.setOAuthPermissions("email,publish_stream,read_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();

		try {
			// facebook.postStatusMessage("Test post status!");
			// System.out.println("OK");
			ResponseList<Post> feed = facebook.getHome();

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
			

			System.out.println(feed.get(0).toString());

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}