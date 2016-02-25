package com.jie.charater.utils;

import java.util.Comparator;

import com.jie.bean.User;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<User> {

	public int compare(User o1, User o2) {
		if (o1.getFirstLetter().equals("@")
				|| o2.getFirstLetter().equals("#")) {
			return -1;
		} else if (o1.getFirstLetter().equals("#")
				|| o2.getFirstLetter().equals("@")) {
			return 1;
		} else {
			return o1.getFirstLetter().compareTo(o2.getFirstLetter());
		}
	}

}
