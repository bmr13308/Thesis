package com.fitnest.followers.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fitnest.followers.model.Follower;

public interface FollowersRepository extends JpaRepository<Follower, Integer> {
	
	@Query("SELECT followerId FROM Follower WHERE subjectId = ?1")
	List<Integer> FindFollowersBySubectId(int subjectId);
	
	@Query("SELECT subjectId FROM Follower WHERE followerId = ?1")
	List<Integer> FindFollowersByFollowerId(int FollowerId);
	
	@Query("SELECT followerId FROM Follower WHERE followerId = ?1 and subjectId = ?2")
	List<Integer> findFollowersByFollowerIdAndSubjectId(int FollowerId,int subjectId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Follower f WHERE f.followerId = :followerId AND f.subjectId = :subjectId")
	int DeleteByFollowerIdAndSubjectId(@Param("followerId") int followerId,@Param("subjectId") int subjectId);
	
	int countBySubjectId(int subjectId);

	int countByFollowerId(int subjectId);
}
