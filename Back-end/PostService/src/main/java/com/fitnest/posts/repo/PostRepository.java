package com.fitnest.posts.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnest.posts.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{
	List<Post> findByUserIdOrderByTimestampDesc(int userId);
	
	List<Post> findByUserIdInOrderByTimestampDesc(List<Integer> userIdList);
}
