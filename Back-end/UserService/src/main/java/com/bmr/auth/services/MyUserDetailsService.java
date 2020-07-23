package com.bmr.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bmr.auth.models.MyUserDetails;
import com.bmr.auth.models.User;
import com.bmr.auth.repo.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Optional<User> user = userRepository.findByEmail(username);
    	user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    	return user.map(MyUserDetails::new).get();
    }
}