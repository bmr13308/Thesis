package com.bmr.profiles.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {
	
	@Value("${PROFILESERVICE_SECRET_KEY}")
	private String servicePassword;
	
	public boolean check(String password) {
		System.out.println("p1: " + password + " p2: " + servicePassword );
       return this.servicePassword.equals(password);

	}

	

}
