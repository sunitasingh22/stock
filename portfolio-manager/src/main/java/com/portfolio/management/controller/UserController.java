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

import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/adduser")
	public ResponseEntity<UserBO> addUser(@RequestBody UserBO user) {
		UserBO createdUser = userService.addUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	 @PostMapping("/login")
	    public ResponseEntity<Map<String, String>> login(@RequestBody UserBO loginUser) {
		 Map<String,String> responseData = new HashMap<>();
	        try {
	            String message = userService.checkUser(loginUser);
	            Long userId = userService.getUserId(loginUser); 
	            responseData.put("message", message);
	            responseData.put("userId", userId.toString());
	            return ResponseEntity.ok(responseData);
	        } catch (IllegalArgumentException e) {
	        	responseData.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
	        }
	    }

}
