package utils;

import java.util.TreeMap;

public interface TopBy {
	void doGet(TreeMap<String, Integer> treeMap, String gId, String from,
			String to);
}
