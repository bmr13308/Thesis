package com.fitnest.posts.model;

import java.io.Serializable;

public class EditComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	public EditComment() {
		// TODO Auto-generated constructor stub
	}
	public EditComment(String text) {
		super();
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
