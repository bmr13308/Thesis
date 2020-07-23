package com.fitnest.posts.model;

import java.io.Serializable;

public class CommentReq implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String text;
	
	private int postId;
	
	
	
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public CommentReq() {
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
