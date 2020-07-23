package com.bmr.auth.services;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bmr.auth.exceptions.EmailAlreadyExistsException;
import com.bmr.auth.models.RegistrationProfileInfo;
import com.bmr.auth.models.RegisterRequest;
import com.bmr.auth.models.User;
import com.bmr.auth.repo.UserRepository;

@Service
public class RegistrationManager {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	
	@Value("${PROFILESERVICE_SECRET_KEY}")
	private String servicePassword;
	
	BCryptPasswordEncoder encoder;
	public RegistrationManager() {
		encoder = new BCryptPasswordEncoder(12);
	};
	RegistrationProfileInfo profileInfo;
	
	
	public void register(RegisterRequest regReq) throws Exception {
		if(regReq.getPassword().length() < 5) throw new Exception("Password should be at least 5 char");
		
		User newUser = new User(regReq.getEmail()
				,encoder.encode(regReq.getPassword()));
		Optional<User> user = userRepository.findByEmail(regReq.getEmail());
    	if(user.isPresent()) throw new EmailAlreadyExistsException("Email already registered.");
		try {
			userRepository.save(newUser);
			User createduser = userRepository.findByEmail(newUser.getEmail()).get();
			int id =createduser.getId();
			System.out.println("user id: " + id);
			try {
				profileInfo = this.createProfileInfo(id,regReq);
				this.sendProfileInformationToPrifleService(profileInfo);
				
			}catch(Exception e) {
				userRepository.deleteById(newUser.getId());
				throw new Exception("Profile Service is down!",e);
			}
		} catch (Exception e) {

			throw new Exception("Registration failure.",e);
		}
		
		
					
	
		
		
	}
	
	private RegistrationProfileInfo createProfileInfo(int id,RegisterRequest regReq) {
		return new RegistrationProfileInfo(id,regReq.getBirth(),regReq.getFirstName(),regReq.getLastName());
	}


	public void sendProfileInformationToPrifleService(RegistrationProfileInfo user) {
		
	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.APPLICATION_JSON);
	      headers.add("password",servicePassword );
	      
	      HttpEntity<RegistrationProfileInfo>  request = 
	    	      new HttpEntity<>(user,headers);
	      
	    System.out.println("asdasdads");
	      
		String s = restTemplate.postForObject("http://ProfilesService/ProfileService/createProfile", request, String.class);
		System.out.println(s);
	}

	
}
