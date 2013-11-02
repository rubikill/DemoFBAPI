package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import managers.FacebookManager;
import models.Spamer;
import models.StatisticInfo;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ByComment;
import utils.ByLiked;
import utils.ByPost;
import utils.Compare;
import utils.CompareSpamer;
import utils.Tools;
import utils.TopBy;
import facebook4j.Group;
import facebook4j.GroupMember;
import facebook4j.Post;
import facebook4j.ResponseList;

public class GroupController extends Controller {
	/*************************************************************
	 * 
	 * Group
	 * 
	 *************************************************************/
	public static Result getGroups() {
		FacebookManager facebookManager = new FacebookManager();
		List<Group> list = facebookManager.getGroups();

		return ok(Tools.listToJson(list));
	}

	public static Result getGroup(String id) {
		FacebookManager facebookManager = new FacebookManager();
		Group group = facebookManager.getGroup(id);

		return ok(Tools.toJson(group));
	}

	public static Result getGroupMember(String id) {
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<GroupMember> members = facebookManager.getGroupMembers(id);

		return ok(Tools.listToJson(members));
	}

	public static Result getGroupFeed(String id) {
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<Post> posts = facebookManager.getGroupFeed(id, 500);

		return ok(Tools.listToJson(postToStatus(posts)));
	}

	private static List<models.Status> postToStatus(ResponseList<Post> posts) {	
		List<models.Status> s = new ArrayList<models.Status>();
		for (Post post : posts) {
			models.Status temp = new models.Status();
			temp.message = post.getMessage();
			temp.type = post.getType();
			temp.url = post.getPicture();
			temp.name = post.getFrom().getName();
			FacebookManager fb = new FacebookManager();
			temp.userAva = fb.getAva(post.getFrom().getId());
			temp.from = post.getFrom();
			temp.id = post.getId();
			temp.comments = fb.getComments(post.getId());
			temp.likes = fb.getLikes(post.getId());
			temp.createdTime = post.getCreatedTime();
			s.add(temp);
		}
		return s;
	}
	
	//getGroupFeed10
	
	public static Result getGroupFeedBefore(String id, String time) {
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<Post> posts = facebookManager.getGroupFeedBefore(id, time);

		return ok(Tools.listToJson(postToStatus(posts)));
	}

	public static Result getTopPost(String id) {
		List<StatisticInfo> res = getTop(id, new ByPost());
		Collections.sort(res, new Compare());
		List<StatisticInfo> res1 = get10(res);

		return ok(Tools.listToJson(res1));
	}

	public static Result getTopComment(String id) {
		List<StatisticInfo> res = getTop(id, new ByComment());
		Collections.sort(res, new Compare());
		List<StatisticInfo> res1 = get10(res);

		return ok(Tools.listToJson(res1));
	}

	public static Result getTopLiked(String id) {
		List<StatisticInfo> res = getTop(id, new ByLiked());
		Collections.sort(res, new Compare());
		List<StatisticInfo> res1 = get10(res);

		return ok(Tools.listToJson(res1));
	}

	public static Result getTopSpam(String id) {
		List<StatisticInfo> res1 = getTop(id, new ByPost());
		List<StatisticInfo> res2 = getTop(id, new ByComment());
		List<StatisticInfo> res3 = getTop(id, new ByLiked());

		List<Spamer> res4 = new ArrayList<Spamer>();

		for (StatisticInfo statisticInfo : res3) {
			if (statisticInfo.count != 0) {
				Spamer s = new Spamer();
				s.user = statisticInfo.user;
				s.avatar = statisticInfo.avatar;
				s.percent = statisticInfo.count;
				res4.add(s);
			}
		}

		HashMap<String, Integer> map1 = new HashMap<String, Integer>();
		HashMap<String, Integer> map2 = new HashMap<String, Integer>();

		for (int i = 0; i < res1.size(); i++) {
			map1.put(res1.get(i).user.getId(), res1.get(i).count);
			map2.put(res2.get(i).user.getId(), res2.get(i).count);
		}

		for (Spamer spamer : res4) {
			if (map1.get(spamer.user.getId()) != null
					&& map2.get(spamer.user.getId()) != null) {
				spamer.percent = (map1.get(spamer.user.getId()) + map2
						.get(spamer.user.getId())) / spamer.percent;
			}
			else{
				spamer.percent = 0;
			}
		}
		
		Collections.sort(res4, new CompareSpamer());
		

		return ok(Tools.listToJson(get10(res4)));
	}

	private static List<StatisticInfo> getTop(String id, TopBy t) {
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<Post> posts = facebookManager.getGroupFeed(id, 500);

		List<StatisticInfo> res = new ArrayList<StatisticInfo>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		t.doGet(posts, map);

		Iterator iter = map.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry mEntry = (Map.Entry) iter.next();
			StatisticInfo info = new StatisticInfo();

			String uId = mEntry.getKey().toString();
			info.user = facebookManager.getUser(uId);
			info.avatar = facebookManager.getAva(uId);
			info.count = Integer.parseInt(mEntry.getValue().toString());
			res.add(info);
		}

		return res;
	}

	private static <T> List<T> get10(List<T> res) {
		List<T> res1 = new ArrayList<T>();
		for (int i = 0; i < res.size(); i++) {
			if (i == 10) {
				break;
			} else {
				res1.add(res.get(i));
			}
		}
		return res1;
	}

	public static Result getGroupPictureURL(String id) {
		FacebookManager facebookManager = new FacebookManager();
		URL group = facebookManager.getGroupPictureURL(id);
		return ok(Tools.toJson(group));
	}
}
