package com.bmr.profiles.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table
public class UserProfile {
	
	@Id
	private int Id;
	private String firstName;
	private String lastName;
	private Date birth;
	private String gender;
	private String sports;
	private String diet;
	private String height;
	private String weight;
	private String profilepicture;
	public UserProfile() {

	}
	public UserProfile(int id, String firstName, String lastName, Date birth) throws Exception {
		super();
		Id = id;
		setFirstName(firstName);
		setLastName(lastName);
		setBirth(birth);
	}
	
	public UserProfile(int userId, UserUpdateReq reqProfile) throws Exception {
		this.setId(userId);
		this.setBirth(reqProfile.getBirth());
		this.setDiet(reqProfile.getDiet());
		this.setFirstName(reqProfile.getFirstName());
		this.setLastName(reqProfile.getLastName());
		this.setGender(reqProfile.getGender());
		this.setHeight(reqProfile.getHeight());
		this.setSports(reqProfile.getSports());
		this.setWeight(reqProfile.getWeight());
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) throws Exception {
		if(firstName.length() < 1) throw new Exception("first name should be at least 1 char");
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) throws Exception {
		if(lastName.length() < 1) throw new Exception("last name should be at least 1 char");
		this.lastName = lastName;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSports() {
		return sports;
	}
	public void setSports(String sports) {
		this.sports = sports;
	}
	public String getDiet() {
		return diet;
	}
	public void setDiet(String diet) {
		this.diet = diet;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getProfilepicture() {
		return profilepicture;
	}
	public void setProfilepicture(String profilepicture) {
		this.profilepicture = profilepicture;
	}
	

}
