package com.fitnest.posts.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnest.posts.model.Comment;
import com.fitnest.posts.model.Post;

public interface CommentRepositroy extends JpaRepository<Comment,Integer>{
	List<Comment> findByPostIdOrderByTimestampDesc(int id);
	
	

}
