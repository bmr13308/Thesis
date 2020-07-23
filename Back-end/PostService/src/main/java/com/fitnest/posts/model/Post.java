package com.fitnest.posts.model;

import java.nio.file.Path;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table
@Entity
public class Post {
@Id
@GeneratedValue
private int id;
@Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
private Date timestamp;
private int userId;
@Column(length = 1000)
private String text;
private String imageSource;
private int likes = 0;

public Post(int id, int userId, String text, String imageSource) {
	super();
	this.id = id;
	this.userId = userId;
	this.text = text;
	this.imageSource = imageSource;
}
public Post(int userId, String text, String imageSource) {
	super();
	this.userId = userId;
	this.text = text;
	this.imageSource = imageSource;
}
public Post() {
}
public void setId(int id) {
	this.id = id;
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
