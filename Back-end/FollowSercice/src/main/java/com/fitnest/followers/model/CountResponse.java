package com.fitnest.followers.model;

public class CountResponse {
	public CountResponse() {
		// TODO Auto-generated constructor stub
	}
	private int count;
	public CountResponse(int followersCount) {
		super();
		this.count = followersCount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int followersCount) {
		this.count = followersCount;
	}
	
}
