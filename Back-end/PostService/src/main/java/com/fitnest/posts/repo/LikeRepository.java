package com.fitnest.posts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fitnest.posts.model.like.PostLike;


public interface LikeRepository extends JpaRepository<PostLike,Integer> {
	
	Optional<PostLike> findOneByUserIdAndPostId(int userId,int postId);
	
	
	@Query("SELECT userId FROM PostLike WHERE postId = ?1")
	List<Integer> findAllUserIdByPostId(int postId);

}
