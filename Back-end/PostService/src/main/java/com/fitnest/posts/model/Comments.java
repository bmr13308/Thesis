package com.fitnest.posts.model;

import java.util.List;

public class Comments {
	private List<Comment> comments;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public Comments() {
		// TODO Auto-generated constructor stub
	}

	public Comments(List<Comment> comments) {
		super();
		this.comments = comments;
	}
	
	

}
