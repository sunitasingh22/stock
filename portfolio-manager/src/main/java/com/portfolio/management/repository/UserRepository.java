package com.portfolio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.management.model.UserBO;

@Repository
public interface UserRepository extends JpaRepository<UserBO, Long> {

	Optional<UserBO> findByEmail(String email);

	Optional<UserBO> findByUsername(String username);
	

}
