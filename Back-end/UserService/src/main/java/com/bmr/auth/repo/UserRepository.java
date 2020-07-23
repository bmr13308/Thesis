package com.bmr.auth.repo;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bmr.auth.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
