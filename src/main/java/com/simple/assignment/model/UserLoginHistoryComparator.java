package com.simple.assignment.model;

import java.util.Comparator;

public class UserLoginHistoryComparator implements Comparator<UserLoginHistory>{

	@Override
	public int compare(UserLoginHistory o1, UserLoginHistory o2) {
		return o1.getLoginDateTime().compareTo(o2.getLoginDateTime());
	}

}
