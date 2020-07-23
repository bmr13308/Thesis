package com.fitnest.posts.model;

import java.io.Serializable;
import java.util.List;

public class Posts  implements Serializable {
	
	private List<Post> posts;
	
	public Posts() {
		// TODO Auto-generated constructor stub
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Posts(List<Post> posts) {
		super();
		this.posts = posts;
	}
	
	

}
