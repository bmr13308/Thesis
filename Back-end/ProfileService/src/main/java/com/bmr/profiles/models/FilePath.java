package com.bmr.profiles.models;

import java.io.Serializable;

public class FilePath implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	public FilePath() {
		// TODO Auto-generated constructor stub
	}
	public FilePath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
