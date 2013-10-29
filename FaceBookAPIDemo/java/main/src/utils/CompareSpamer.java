package utils;

import java.util.Comparator;

import models.Spamer;

public class CompareSpamer implements Comparator<Spamer>{

	@Override
	public int compare(Spamer s1, Spamer s2) {
		// TODO Auto-generated method stub
		if(s1.percent > s2.percent){
			return -1;
		}
		
		if(s1.percent < s2.percent){
			return 1;
		}
		
		return 0;
	}

}
