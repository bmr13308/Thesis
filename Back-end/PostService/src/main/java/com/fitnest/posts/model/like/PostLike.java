package com.fitnest.posts.model.like;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PostLike {

	@Id
	@GeneratedValue
	private int id;
	
	private int postId;
	private int userId;
 public PostLike() {
	// TODO Auto-generated constructor stub
}
	public PostLike(int postId, int userId) {
		super();
		this.postId = postId;
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
