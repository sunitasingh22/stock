package com.portfolio.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.portfolio.management.controller.service.impl.UserServiceImpl;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.UserRepository;

public class UserServiceUnitTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	private UserBO user;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		// Initialize test data
		user = new UserBO();
		user.setId(1L);
		user.setEmail("sai@gmail.com");
		user.setUsername("sai");
		user.setPassword("password");
		when(userRepository.findByUsername("sai")).thenReturn(Optional.of(user));
	}

	@Test
	public void testAddUser() {
		// Mock repository to return empty Optional to simulate "username does not exist"
	    when(userRepository.findByUsername("sai")).thenReturn(Optional.empty());
	    
	    // Mock save operation to return the user object
	    when(userRepository.save(any(UserBO.class))).thenReturn(user);

	    UserBO savedUser = userService.addUser(user);

	    // Assert
	    assertThat(savedUser).isNotNull();
	    assertThat(savedUser.getId()).isEqualTo(1L);
	    assertThat(savedUser.getEmail()).isEqualTo("sai@gmail.com");
	}

	@Test
	public void testCheckUserForValidUser() {
		UserBO loginUser = new UserBO();
		loginUser.setUsername("sai");
		loginUser.setPassword("password");

		String result = userService.checkUser(loginUser);

		assertThat(result).isEqualTo("Login successful");
	}

	@Test
	public void testGetUserId() {
		UserBO loginUser = new UserBO();
		loginUser.setUsername("sai");
		loginUser.setPassword("password");

		Long userId = userService.getUserId(loginUser);

		assertNotNull(userId);
		assertThat(userId).isEqualTo(user.getId());
	}

}
