package com.portfolio.management.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.management.dto.Users;
import com.portfolio.management.mapper.UserMapper;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/adduser")
	public ResponseEntity<Users> addUser(@RequestBody UserBO user) {
		 log.info("Request to add a new user: {}", user.getUsername());
		UserBO createdUser = userService.addUser(user);
		//convert BO object to dto for response entity
		 Users userAdded = UserMapper.INSTANCE.userBOToDto(createdUser);
		 log.info("New user created successfully with userId: {}", userAdded.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(userAdded);
	}

	 @PostMapping("/login")
	    public ResponseEntity<Map<String, String>> login(@RequestBody UserBO loginUser) {
		 log.info("Login request received for user: {}", loginUser.getUsername());
		 Map<String,String> responseData = new HashMap<>();
	        try {
	            String message = userService.checkUser(loginUser);
	            Long userId = userService.getUserId(loginUser); 
	            responseData.put("message", message);
	            responseData.put("userId", userId.toString());
	            return ResponseEntity.ok(responseData);
	        } catch (IllegalArgumentException e) {
	        	log.warn("Login failed for user: {}. error Message: {}", loginUser.getUsername(), e.getMessage());
	        	responseData.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
	        }
	    }

}
