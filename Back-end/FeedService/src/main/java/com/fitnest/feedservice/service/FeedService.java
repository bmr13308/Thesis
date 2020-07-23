package com.fitnest.feedservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fitnest.feedservice.model.FollowingResponse;
import com.fitnest.feedservice.model.Post;
import com.fitnest.feedservice.model.Posts;
import com.fitnest.feedservice.model.UserIdList;

@Service
public class FeedService {
	@Autowired
	RestTemplate restTemplate;
	

	final String FOLLOWSERVICEURL = "http://FollowerService/FollowService";
	final String POSTSERVICEURL = "http://PostService/PostService";
	private int postNum = 10;

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}



	public List<Post> getFeedFromIdDesc(int userId,int lastpostid) {
		
		UserIdList following =  new UserIdList();
		following.setUserIdList(getFollowingId(userId));
		
		Posts posts = restTemplate.postForObject(POSTSERVICEURL + "/getposts", following, Posts.class);
				
		List<Post> postlist = posts.getPosts();
				
		List<Post> morepost = new ArrayList(); 
		int i;
		for (i = 0; i < postlist.size(); i++) {
			
			if(lastpostid == 0) break;
			
			if(postlist.get(i).getId() == lastpostid) {
				break;
			}
		}
		
		
		int j = (userId == 0)?i + 1:i;
		
		
		int pN = this.postNum;
		while(j < postlist.size() && pN > 0) {
			Post post = postlist.get(j);
			morepost.add(post);
			pN--;
			j++;
		}

		
		
		return morepost;
	}
	
	
	private List<Integer> getFollowingId(int userId){

		FollowingResponse response = restTemplate.getForObject(FOLLOWSERVICEURL + "/following/" + userId, FollowingResponse.class);
		
		return response.getFollowing();
	}
	
	
	


}
