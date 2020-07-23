package com.bmr.auth.models;

import java.sql.Date;
import java.util.Calendar;

public class RegistrationProfileInfo {
	private int id;

	private Date birth;
    private String firstName;
    private String lastName;
    

    public RegistrationProfileInfo(int id, Date birth, String firstName, String lastName) {
		super();
		this.id = id;
		this.birth = birth;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) throws Exception {
		java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		if(birth.before(now))
		this.birth = birth;
		else throw new Exception("Wait for the birth.");
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
