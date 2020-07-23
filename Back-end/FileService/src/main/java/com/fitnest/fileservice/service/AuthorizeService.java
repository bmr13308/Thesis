package com.fitnest.fileservice.service;

import javax.websocket.EncodeException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {
	
	@Value("${FILESERVICE_SECRET_KEY}")
	private String servicePassword;
	
	public boolean check(String password) {
		
       return this.servicePassword.equals(password);
		
	}

	

}
