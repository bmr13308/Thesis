package com.fitnest.followers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnest.followers.model.FollowReq;
import com.fitnest.followers.model.Follower;
import com.fitnest.followers.model.CountResponse;
import com.fitnest.followers.model.FollowersResponse;
import com.fitnest.followers.model.FollowingResponse;
import com.fitnest.followers.repo.FollowersRepository;
import com.fitnest.followers.service.JwtUtil;
import com.fitnest.followers.service.UserValidator;

@RestController
@RequestMapping("/FollowService")
public class FollowersServiceController {

	@Autowired
	JwtUtil jwt;
	
	@Autowired
	FollowersRepository followersRepository;
	@Autowired
	UserValidator userValidator;
	
	@GetMapping("/following/{followerId}")
	public ResponseEntity<?> GetFollowing(@PathVariable int followerId) {
		List<Integer> followers = followersRepository.FindFollowersByFollowerId(followerId);
		return ResponseEntity.ok(new FollowingResponse(followers));
	}
	
	@GetMapping("/followingCount/{followerId}")
	public ResponseEntity<?> GetFollowingCount(@PathVariable int followerId) {
		int followersCount = followersRepository.countByFollowerId(followerId);
		return ResponseEntity.ok(new CountResponse(followersCount));
	}
	
	@GetMapping("/followers/{subjectId}")
	public ResponseEntity<?> GetFollowers(@PathVariable int subjectId) {
		List<Integer> followers = followersRepository.FindFollowersBySubectId(subjectId);
		return ResponseEntity.ok(new FollowersResponse(followers));
	}
	
	@GetMapping("/followersCount/{subjectId}")
	public ResponseEntity<?> GetFollowersCount(@PathVariable int subjectId) {
		int followersCount = followersRepository.countBySubjectId(subjectId);
		return ResponseEntity.ok(new CountResponse(followersCount));
	}
	
	@PostMapping("/follow/{followId}")
	public ResponseEntity<?> Follow(@PathVariable int followId,@RequestHeader String Authorization) throws Exception{
		//precondition: the subject not yet followed,the subject is valid user,the subject already followed
		
		int id = followId;
		if(!userValidator.validate(id)) throw new Exception("Invalid Subject Id");
		int UserId = jwt.extractSubjectId(Authorization.split(" ")[1]);
		Follower follower = new Follower(UserId,id);
		if(!followersRepository.findFollowersByFollowerIdAndSubjectId(UserId,id).isEmpty()) throw new Exception("User already followed");
		if(UserId == id) throw new Exception("You cant follow yourself");
		followersRepository.save(follower);
		return ResponseEntity.ok("Followed");
	}
	
	@DeleteMapping("/unfollow/{followId}")
	public ResponseEntity<?> UnFollow(@PathVariable int followId,@RequestHeader String Authorization){
		
		int followerId = jwt.extractSubjectId(Authorization.split(" ")[1]);
		int id = followId;
		int subjectId = id;
		int deletedRows = followersRepository.DeleteByFollowerIdAndSubjectId(followerId, subjectId);
		if(deletedRows > 0) {
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}
	
}
