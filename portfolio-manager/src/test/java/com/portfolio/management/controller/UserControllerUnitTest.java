package com.portfolio.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.portfolio.management.dto.Users;
import com.portfolio.management.mapper.UserMapper;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.UserService;

class UserControllerUnitTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private Users user;
	

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		user = new Users();
		user.setId(1);
		user.setUsername("sai");
		user.setPassword("password");
		user.setEmail("sai@gmail.com");
	}

	@Test
	public void testAddUser() {
		UserBO userObj = UserMapper.INSTANCE.userDtoToBO(user);
		when(userService.addUser(userObj)).thenReturn(userObj);

		ResponseEntity<Users> response = userController.addUser(userObj);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());
		verify(userService, times(1)).addUser(userObj);
	}

	@Test
	public void testLoginSuccess() {
		UserBO userObj = UserMapper.INSTANCE.userDtoToBO(user);
		when(userService.checkUser(userObj)).thenReturn("Login successful");
		when(userService.getUserId(userObj)).thenReturn(1L);

		ResponseEntity<Map<String, String>> response = userController.login(userObj);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login successful", response.getBody().get("message"));
		assertEquals("1", response.getBody().get("userId"));
		verify(userService, times(1)).checkUser(userObj);
		verify(userService, times(1)).getUserId(userObj);
	}

	@Test
	public void testLoginFailure() {
		UserBO userObj = UserMapper.INSTANCE.userDtoToBO(user);
		when(userService.checkUser(userObj)).thenThrow(new IllegalArgumentException("Invalid credentials"));

		ResponseEntity<Map<String, String>> response = userController.login(userObj);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Invalid credentials", response.getBody().get("error"));
		verify(userService, times(1)).checkUser(userObj);
		verify(userService, times(0)).getUserId(userObj);
	}
}
