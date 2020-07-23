package com.bmr.auth.models;

import java.sql.Date;

public class RegisterRequest {
	private String email;
    private String password;
    private Date birth;
    private String firstName;
    private String lastName;
    
	public RegisterRequest() {

	}
	
	public RegisterRequest(String email, String password, Date birth, String fistName, String lastName) {
		this.email = email;
		this.password = password;
		this.birth = birth;
		this.firstName = fistName;
		this.lastName = lastName;
	}
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String fistName) {
		this.firstName = fistName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    

}
