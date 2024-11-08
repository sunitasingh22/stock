package com.portfolio.management.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserRepository userRepository;

    public UserBO addUser(UserBO user) {
    	log.info("Adding new user with email: {} and username: {}", user.getEmail(), user.getUsername());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        	 log.warn("Email {} already exists", user.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
        	 log.warn("Username {} already exists", user.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }

        UserBO newUser = userRepository.save(user);
        log.info("New user created successfully with userId: {}", newUser.getId());
        return newUser;
    }
    
    public UserBO checkUser(UserBO loginUser) {
    	 log.info("Check User is valid for Login for username: {}", loginUser.getUsername());
        Optional<UserBO> userData = userRepository.findByUsername(loginUser.getUsername());
        
        if (userData.isPresent()) {
            UserBO user = userData.get();
            if (user.getPassword().equals(loginUser.getPassword())) {
            	 log.info("Login successful for username: {}", loginUser.getUsername());
                return user;
            } else {
            	 log.warn("Invalid login for username: {}", loginUser.getUsername());
                throw new IllegalArgumentException("Invalid credentials");
            }
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

	

}
