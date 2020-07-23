package com.fitnest.feedservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitnest.feedservice.model.Post;
import com.fitnest.feedservice.model.Posts;
import com.fitnest.feedservice.service.FeedService;
import com.fitnest.feedservice.service.JwtUtil;



@RestController
@RequestMapping("/FeedService")
public class FeedController {

@Autowired
JwtUtil jwt;
	

@Autowired
FeedService feedService;
	

@GetMapping("/getfeed")
public ResponseEntity<Posts> getFeedFromIdDesc(@RequestHeader String authorization,@RequestParam int lastPostId){
	String token = authorization.split(" ")[1];
	int userId = jwt.extractSubjectId(token);
	
	List<Post> posts = feedService.getFeedFromIdDesc(userId,lastPostId);
	return ResponseEntity.ok( new Posts(posts));
	
}



}
