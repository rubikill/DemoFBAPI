package managers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import utils.Global;
import utils.Tools;
import facebook4j.Admin;
import facebook4j.Album;
import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Group;
import facebook4j.GroupMember;
import facebook4j.Like;
import facebook4j.Media;
import facebook4j.Photo;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookManager {

	public Facebook facebook;

	public FacebookManager() {
		// TODO Auto-generated constructor stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAppId(Global.OAuthAppId)
				.setOAuthAppSecret(Global.OAuthAppSecret)
				.setOAuthAccessToken(Global.OAuthAccessToken)
				.setOAuthPermissions(
						"email,user_notes,user_status,publish_actions,user_groups,user_likes,"
								+ "user_photos,user_about_me,user_birthday,user_friends,user_hometown,"
								+ "user_location,user_videos,create_note,manage_friendlists,photo_upload,"
								+ "read_requests,share_item,export_stream,manage_notifications,"
								+ "publish_stream,read_mailbox,read_stream,video_upload,manage_pages,"
								+ "read_friendlists,read_page_mailboxes,status_update,page,...");

		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();

	}

	/*************************************************************
	 * 
	 * Status
	 * 
	 *************************************************************/
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

	public boolean postPhoto(Media media) {
		if (Global.OAuthAccessToken != null) {
			try {
				facebook.postPhoto(media);

				return true;
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}

		}
		return false;
	}

	/*************************************************************
	 * 
	 * Feed
	 * 
	 *************************************************************/
	public ResponseList<Post> getFeeds() {
		if (Global.OAuthAccessToken != null) {
			try {
				System.out.println("Get feed");
				return facebook.getHome(new Reading().limit(10));
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/*************************************************************
	 * 
	 * Album
	 * 
	 *************************************************************/
	public Photo getPhoto(String id) {
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getPhoto(id);
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Photo> getPhotos(String id) {
		if (Global.OAuthAccessToken != null) {
			try {
				System.out.println(facebook.getAlbumPhotos(id).size());
				return facebook.getAlbumPhotos(id);

			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public ResponseList<Album> getAlbums() {
		if (Global.OAuthAccessToken != null) {
			try {
				ResponseList<Album> res = facebook.getAlbums((new Reading())
						.limit(5));
				return res;
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/*************************************************************
	 * 
	 * Status
	 * 
	 *************************************************************/
	public List<String> getStatus() {
		if (Global.OAuthAccessToken != null) {
			try {
				ResponseList<Post> list = facebook.getStatuses((new Reading()
						.limit(3)));
				List<String> res = new ArrayList<String>();
				for (Post post : list) {
					res.add(post.getMessage());
				}

				return res;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	/*************************************************************
	 * 
	 * Like/unlike
	 * 
	 *************************************************************/
	public void like(String id) {
		if (Global.OAuthAccessToken != null) {
			try {
				System.out.println(id);
				facebook.likePost(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void unLike(String id) {
		if (Global.OAuthAccessToken != null) {
			try {
				facebook.unlikePost(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public User getUser(String id) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getUser(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	/*************************************************************
	 * 
	 * Page
	 * 
	 *************************************************************/
	public ResponseList<Admin> getPages() {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				ResponseList<Admin> list = facebook.getPageAdmins();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}

	public URL getAva(String userId) {
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getPictureURL(userId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	/*************************************************************
	 * 
	 * Group
	 * 
	 *************************************************************/
	public ResponseList<Group> getGroups() {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				// System.out.println(facebook.getPermissions());

				return facebook.getGroups();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public Group getGroup(String id) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {

				return facebook.getGroup(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public ResponseList<Post> getGroupFeed(String groupId) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getGroupFeed(
						groupId,
						new Reading().since("11 September 2000").limit(
								Global.limit));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public List<models.Post> getGroupFeedBetweenFQL(String groupId, String from,
			String to) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				List<models.Post> posts = getPostsFQL(groupId, from, to);
				for (models.Post post : posts) {
					post.comments = getCommentsFQL(post.post_id);
				}
				
				return posts;

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	
	public ResponseList<Post> getGroupFeedBetween(String groupId, String from,
			String to) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getGroupFeed(groupId, new Reading().since(from)
						.until(to).limit(Global.limit));

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public List<models.Post> getPostsFQL(String gId, String from, String to) {
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			Date dateFrom = format.parse(from);
			Date dateTo = format.parse(to);
			long millisFrom = dateFrom.getTime() / 1000;
			long millisTo = dateTo.getTime() / 1000;
			
			String fql = "SELECT post_id, actor_id, likes.count, message FROM stream WHERE source_id = '"
					+ gId
					+ "' AND created_time >= "
					+ millisFrom
					+ " AND created_time <= " + millisTo + " LIMIT 1000";

			return Tools.listFromJson(facebook.executeFQL(fql).toString(),
					models.Post.class);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public List<models.Comment> getPostsCommentsFQL(String gId, String from, String to) {
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			Date dateFrom = format.parse(from);
			Date dateTo = format.parse(to);
			long millisFrom = dateFrom.getTime() / 1000;
			long millisTo = dateTo.getTime() / 1000;

			String fql = "SELECT fromid, id, likes FROM comment WHERE post_id IN (SELECT post_id, actor_id, likes.count FROM stream WHERE source_id = '"
					+ gId
					+ "' AND created_time >= "
					+ millisFrom
					+ " AND created_time <= " + millisTo + " LIMIT 1000)";

			return Tools.listFromJson(facebook.executeFQL(fql).toString(),
					models.Comment.class);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<models.Comment> getCommentsFQL(String pId) {
		String fql = "SELECT fromid, id, likes, text FROM comment WHERE post_id = '"
				+ pId + "' LIMIT 1000";
		try {
			//System.out.println(facebook.executeFQL(fql).toString());
			return Tools.listFromJson(facebook.executeFQL(fql).toString(),
					models.Comment.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ResponseList<Post> getGroupFeedBefore(String groupId, String from, String to) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				ResponseList<Post> p = facebook.getGroupFeed(groupId,
						new Reading().since(from).until(to).limit(10));
				
				return p;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public ResponseList<GroupMember> getGroupMembers(String groupId) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getGroupMembers(groupId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public ResponseList<Comment> getComments(String id) {
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook
						.getPostComments(id, (new Reading()).limit(1000));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public ResponseList<Like> getLikes(String id) {
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getPostLikes(id, (new Reading()).limit(1000));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public int getLikesCount(String id) {
		try {
			return facebook.getPostLikes(id,
					(new Reading().fields("id")).limit(1000)).size();
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public URL getGroupPictureURL(String groupId) {
		// TODO Auto-generated method stub
		if (Global.OAuthAccessToken != null) {
			try {
				return facebook.getGroupPictureURL(groupId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
}
