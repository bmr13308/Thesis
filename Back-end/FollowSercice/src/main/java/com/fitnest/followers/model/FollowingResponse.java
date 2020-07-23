package com.fitnest.followers.model;

import java.util.List;

public class FollowingResponse {
public FollowingResponse() {
	// TODO Auto-generated constructor stub
}
private List<Integer> following;
public List<Integer> getFollowing() {
	return following;
}
public void setFollowing(List<Integer> following) {
	this.following = following;
}
public FollowingResponse(List<Integer> following) {
	super();
	this.following = following;
}

}
