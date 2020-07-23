package com.bmr.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bmr.auth.exceptions.EmailAlreadyExistsException;
import com.bmr.auth.models.AuthenticationRequest;
import com.bmr.auth.models.AuthenticationResponse;
import com.bmr.auth.models.MyUserDetails;
import com.bmr.auth.models.RegisterRequest;
import com.bmr.auth.models.User;
import com.bmr.auth.repo.UserRepository;
import com.bmr.auth.services.JwtUtil;
import com.bmr.auth.services.MyUserDetailsService;
import com.bmr.auth.services.RegistrationManager;



@RestController
@RequestMapping("/Auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService RegistrationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private RegistrationManager registrationManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/ison")
	public String IsOn() {
		return "It's on! :)";
	}
	
	@GetMapping("/hello")
	public String Hello() {
		return "Hello";
	}
	
	@GetMapping("/ValidateUser/{userId}")
	public ResponseEntity<?> ValidateUserId(@PathVariable int userId){
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) return ResponseEntity.ok().body("success");
		else 
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/getuserid")
	public ResponseEntity<?> ValidateUserId(@RequestParam String email){
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent()) return ResponseEntity.ok().body(user.get().getId());
		else 
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final MyUserDetails userDetails = (MyUserDetails)userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> Register(@RequestBody RegisterRequest regReq) throws Exception{
		try {
			registrationManager.register(regReq);
		
		}
		catch (EmailAlreadyExistsException e) {
			throw new Exception(e);
		}
		catch (Exception e) {
			throw new Exception("Registration Failure", e);
		}
		return ResponseEntity.ok("registered");
	}
	
	


}
