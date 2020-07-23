package com.fitnest.posts.model;

import java.io.Serializable;
import java.util.Date;

public class PostResponse implements Serializable{
	private int userId;
	private String text;
	private String imageSource;
	private Date created;
	private int likes;
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public PostResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public PostResponse(Post post) {
		super();
		this.userId = post.getUserId();
		this.text = post.getText();
		this.imageSource = post.getImageSource();
		this.created = post.getTimestamp();
		this.likes= post.getLikes();
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImageSource() {
		return imageSource;
	}
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
