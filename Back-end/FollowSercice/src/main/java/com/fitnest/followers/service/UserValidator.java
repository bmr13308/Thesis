package com.fitnest.followers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserValidator {
	@Autowired
	RestTemplate restTemplate;
	public UserValidator() {
		// TODO Auto-generated constructor stub
	}
	public boolean validate(int userId) {
		try {
			ResponseEntity<String> response =  restTemplate.getForEntity("http://AuthenticationService/Auth/ValidateUser/" + userId, String.class);
			if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
				return false;
			}
			else return true;
		} catch (RestClientException e) {
			e.printStackTrace();
			return false;
		}
	}
}
