package com.fitnest.feedservice.model;

import java.nio.file.Path;
import java.util.Date;



public class Post {
private int id;
private Date timestamp;
private int userId;
private String text;
private String imageSource;
private int likes;

public Post(int id, int userId, String text, String imageSourcen, int likes) {
	super();
	this.id = id;
	this.userId = userId;
	this.text = text;
	this.imageSource = imageSource;
	this.likes = likes;
}
public Post(int userId, String text, String imageSource) {
	super();
	this.userId = userId;
	this.text = text;
	this.imageSource = imageSource;
}
public Post() {
}
public int getId() {
	return id;
}
public Date getTimestamp() {
	return timestamp;
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
public int getLikes() {
	return likes;
}
public void setLikes(int likes) {
	this.likes = likes;
}


}
