package com.bmr.auth;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.bmr.auth.models.User;
import com.bmr.auth.repo.UserRepository;

import io.jsonwebtoken.lang.Assert;

@SpringBootTest
class AuthenticationSerciceApplicationTests {

	
	@Autowired
	UserRepository userrep;
	
	@Autowired
	Environment env;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void findUser() {
		Optional<User> user = userrep.findById(1);
	
		System.out.println(user);
		
	}
	
	@Test
	void WriteSecretKey() {
		
		//System.out.println("secret key: " + env.getProperty("JWT_SECRET_KEY"));
	}


}
