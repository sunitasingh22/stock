package com.portfolio.management.controller.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserRepository userRepository;

    public UserBO addUser(UserBO user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        return userRepository.save(user);
    }
    
    public String checkUser(UserBO loginUser) {
        Optional<UserBO> userData = userRepository.findByUsername(loginUser.getUsername());
        
        if (userData.isPresent()) {
            UserBO user = userData.get();
            if (user.getPassword().equals(loginUser.getPassword())) {
                return "Login successful";
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

	public Long getUserId(UserBO loginUser) {
        // Logic to find the user by username or email and return the user ID
        Optional<UserBO> user = userRepository.findByUsername(loginUser.getUsername());
        return user.map(UserBO::getId).orElse(null);
    }

}
