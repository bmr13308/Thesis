package com.fitnest.posts.model;

import java.io.Serializable;

public class PostEdit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String text;
	
	public PostEdit() {
		// TODO Auto-generated constructor stub
	}

	public PostEdit(int id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
