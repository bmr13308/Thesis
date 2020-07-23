package com.fitnest.followers.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class Follower {
@GeneratedValue
@Id
private int id;
private int followerId;
private int subjectId;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getFollowerId() {
	return followerId;
}
public void setFollowerId(int followerId) {
	this.followerId = followerId;
}
public int getSubjectId() {
	return subjectId;
}
public void setSubjectId(int subjectId) {
	this.subjectId = subjectId;
}
public Follower() {
	// TODO Auto-generated constructor stub
}
public Follower(int followerId, int subjectId) {
	this.followerId = followerId;
	this.subjectId = subjectId;
}



}
