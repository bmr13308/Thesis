package com.bmr.profiles.controllers;

import java.sql.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bmr.profiles.models.RegistrationProfileInfo;
import com.bmr.profiles.models.UserProfile;
import com.bmr.profiles.models.UserUpdateReq;
import com.bmr.profiles.repo.UserProfileRepository;
import com.bmr.profiles.services.AuthorizeService;
import com.bmr.profiles.services.FileService;
import com.bmr.profiles.services.JwtUtil;
import com.bmr.profiles.services.UserProfileManager;
import com.thoughtworks.xstream.security.ForbiddenClassException;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ProfileService")
public class ProfileServiceController {

	@Autowired
	UserProfileRepository profileManager;
	
	@Autowired
	JwtUtil jwt;
	
	@Autowired
	FileService imgService;
	
	@Autowired
	AuthorizeService authService;
	
	@PostMapping("/createProfile")
	public ResponseEntity<?> CreateProfile(@RequestHeader String password,@RequestBody RegistrationProfileInfo profinfo) throws Exception {
		System.out.println(password);
		if(!authService.check(password)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
		
		//Must have security
		System.out.println(profinfo);
		UserProfile userProfile = new UserProfile(profinfo.getId(),profinfo.getFirstName(),profinfo.getLastName(),profinfo.getBirth());
		profileManager.save(userProfile);
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("/userprofile/{userId}")
	public ResponseEntity<?> GetUserProfile(@PathVariable int userId){
		Optional<UserProfile> response = profileManager.findById(userId);
		
		if(response.isPresent())
		return ResponseEntity.ok(response);
		else return  ResponseEntity.notFound().build();
	}
	

	
	@PutMapping("/editProfile")
	public ResponseEntity<String> UpdateUserProfile(@RequestBody UserUpdateReq reqProfile,@RequestHeader String Authorization) throws Exception{
		int UserId = jwt.extractSubjectId(Authorization.split(" ")[1]);
		Optional<UserProfile> profileToUpdate = profileManager.findById(UserId);
		if(!profileToUpdate.isPresent()) { ResponseEntity.notFound().build();}
		
		UserProfile profile = profileToUpdate.get();
		
		profile.setFirstName(reqProfile.getFirstName());
		profile.setLastName(reqProfile.getLastName());
		profile.setBirth(reqProfile.getBirth());
		profile.setGender(reqProfile.getGender());
		profile.setSports(reqProfile.getSports());
		profile.setDiet(reqProfile.getDiet());
		profile.setHeight(reqProfile.getHeight());
		profile.setWeight(reqProfile.getWeight());


		profileManager.save(profile);
		return ResponseEntity.ok("Success");
		
	}
	
	
	@PostMapping("/uploadProfilePicture")
	public ResponseEntity<?> Uploadprofilepicture(@RequestHeader String Authorization,@RequestParam MultipartFile imageFile){
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);
		
		Optional<UserProfile> profileToUpdate = profileManager.findById(userId);
		
		if(!profileToUpdate.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile not found");
		
		String path;
		try {
			path = imgService.saveFile(imageFile);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Service error",HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		UserProfile prof = profileToUpdate.get();
		prof.setProfilepicture(path);
		profileManager.save(prof);
		return ResponseEntity.ok().body("Success");
	}
	
	@PostMapping("/deleteProfilePicture")
	public ResponseEntity<?> RemoveProfilepicture(@RequestHeader String Authorization){
		
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);
		
		Optional<UserProfile> profileToUpdate = profileManager.findById(userId);
		
		if(!profileToUpdate.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile not found");
		
		imgService.deleteFile(profileToUpdate.get().getProfilepicture());
		
		return ResponseEntity.ok().body("Success");
		
	}
	
	
}
