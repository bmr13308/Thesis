package com.fitnest.posts.model;

import java.io.Serializable;
import java.util.List;

public class UserIdList implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private List<Integer> UserIdList;

	
	public UserIdList() {
		
		
	}


	public List<Integer> getUserIdList() {
		return UserIdList;
	}


	public void setUserIdList(List<Integer> userIdList) {
		UserIdList = userIdList;
	}

}
