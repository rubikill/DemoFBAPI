package utils;

import java.util.Comparator;

import models.StatisticInfo;

public class Compare implements Comparator<StatisticInfo> {

	@Override
	public int compare(StatisticInfo o1, StatisticInfo o2) {
		if(o1.count > o2.count){
			return -1;
		}
		
		if(o1.count < o2.count){
			return 1;
		}
		
		return 0;
	}

}