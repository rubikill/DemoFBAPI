package managers;

import java.util.HashMap;
import java.util.List;

import facebook4j.Post;

import models.StatisticInfo;

public class Statistics {

	public List<StatisticInfo> doStatistic(String groupId) {
		List<StatisticInfo> res = null;

		FacebookManager facebookManager = new FacebookManager();
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (Post post : facebookManager.getGroupFeed(groupId)) {
			String name = post.getName();
			if (!map.containsKey(name)) {
				map.put(name, 1);
			} else {
				int value = map.get(name) + 1;
				map.put(name, value);
			}
		}
		
		System.out.println(map.entrySet());

		return res;
	}

}
