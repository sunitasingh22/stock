package com.portfolio.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/adduser")
	public ResponseEntity<Void> addUser(@RequestBody Users user) {
		log.info("Request to add a new user: {}", user.getUsername());
		// convert dto object to BO for DB operations
		UserBO userBO = UserMapper.INSTANCE.userDtoToBO(user);
		UserBO createdUser = userService.addUser(userBO);
		
		log.info("New user created successfully with userId: {}", createdUser.getEmail());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/login")
	public ResponseEntity<Users> login(@RequestBody Users loginUser) {
		log.info("Login request received for user: {}", loginUser.getUsername());
		// convert dto object to BO for DB operations
		UserBO userBO = UserMapper.INSTANCE.userDtoToBO(loginUser);
		UserBO verifiedUser = userService.checkUser(userBO);
		// convert BO object to DTO for response entity
		Users userData = UserMapper.INSTANCE.userBOToDto(verifiedUser);
		return ResponseEntity.ok(userData);
	}

}
