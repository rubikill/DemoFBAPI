package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import managers.FacebookManager;
import models.Spamer;
import models.StatisticInfo;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ByComment;
import utils.ByLiked;
import utils.ByPost;
import utils.BySpam;
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
		ResponseList<Post> posts = facebookManager.getGroupFeed(id);

		return ok(Tools.listToJson(postToStatus(posts)));
	}

	private static List<models.Status> postToStatus(ResponseList<Post> posts) {
		List<models.Status> s = new ArrayList<models.Status>();
		for (Post post : posts) {
			models.Status temp = new models.Status();
			temp.message = post.getMessage();
			temp.type = post.getType();
			temp.url = post.getPicture();
			FacebookManager fb = new FacebookManager();
			temp.from = post.getFrom();
			temp.id = post.getId();
			temp.comments = fb.getComments(post.getId());
			temp.likes = fb.getLikes(post.getId());
			temp.createdTime = post.getCreatedTime();
			s.add(temp);
		}
		return s;
	}

	public static Result getGroupFeedBetween(String id, String from, String to) {
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<Post> posts = facebookManager.getGroupFeedBetween(id,
				from, to);
		return ok(Tools.listToJson(postToStatus(posts)));
	}

	public static Result getGroupFeedBefore(String id, String from, String to) {
		FacebookManager facebookManager = new FacebookManager();
		ResponseList<Post> posts = facebookManager.getGroupFeedBefore(id, from, to);

		return ok(Tools.listToJson(postToStatus(posts)));
	}

	public static Result getTopPost(String id, String from, String to) {
		List<StatisticInfo> res = getTop(id, new ByPost(), from, to);
		
		return ok(Tools.listToJson(res));
	}

	public static Result getTopComment(String id, String from, String to) {
		List<StatisticInfo> res = getTop(id, new ByComment(), from, to);
		
		return ok(Tools.listToJson(res));
	}

	public static Result getTopLiked(String id, String from, String to) {
		List<StatisticInfo> res = getTop(id, new ByLiked(), from, to);
		
		return ok(Tools.listToJson(res));
	}

	public static Result getTopSpam(String id, String from, String to) {
		TreeMap treeMap = new TreeMap();		
		new BySpam().doGet(treeMap, id, from, to);

		Map<String, Float> sortedMap = sortByComparator(treeMap);

		Iterator iter = sortedMap.entrySet().iterator();
		int i = 0;

		FacebookManager facebookManager = new FacebookManager();
		List<Spamer> res = new ArrayList<Spamer>();
		while (iter.hasNext()) {
			if (i > 9) {
				break;
			}
			Map.Entry mEntry = (Map.Entry) iter.next();
			Spamer info = new Spamer();

			info.id = mEntry.getKey().toString();
			info.name = facebookManager.getUser(info.id).getName();
			info.percent = Float.parseFloat(mEntry.getValue().toString());
			res.add(info);
			i++;
		}

		return ok(Tools.listToJson(res));
	}

	private static List<StatisticInfo> getTop(String id, TopBy t, String from,
			String to) {
		TreeMap treeMap = new TreeMap();		
		t.doGet(treeMap, id, from, to);

		Map<String, String> sortedMap = sortByComparator(treeMap);

		Iterator iter = sortedMap.entrySet().iterator();
		int i = 0;

		FacebookManager facebookManager = new FacebookManager();
		List<StatisticInfo> res = new ArrayList<StatisticInfo>();
		while (iter.hasNext()) {
			if (i > 9) {
				break;
			}
			Map.Entry mEntry = (Map.Entry) iter.next();
			StatisticInfo info = new StatisticInfo();

			info.id = mEntry.getKey().toString();
			info.name = facebookManager.getUser(info.id).getName();
			info.count = Integer.parseInt(mEntry.getValue().toString());
			res.add(info);
			i++;
		}

		return res;
	}

	private static Map sortByComparator(Map unsortMap) {

		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		// put sorted list into map again
		// LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static void printMap(Map<String, String> map) {
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}
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
