package com.bmr.profiles.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bmr.profiles.models.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	
}
