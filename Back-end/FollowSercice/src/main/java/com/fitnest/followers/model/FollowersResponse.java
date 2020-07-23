package com.fitnest.followers.model;

import java.util.List;

public class FollowersResponse {
private List<Integer> followers;
public FollowersResponse() {
	// TODO Auto-generated constructor stub
}
public FollowersResponse(List<Integer> followers) {
	super();
	this.followers = followers;
}
public List<Integer> getFollowers() {
	return followers;
}
public void setFollowers(List<Integer> followers) {
	this.followers = followers;
}

}
