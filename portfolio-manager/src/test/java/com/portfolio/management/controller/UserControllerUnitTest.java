package com.portfolio.management.controller;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockService;
import com.portfolio.management.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerUnitTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private UserBO user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		user = new UserBO();
		user.setUsername("user1");
		user.setPassword("password");
	}

	@Test
	public void testAddUser() {
		when(userService.addUser(user)).thenReturn(user);

		ResponseEntity<UserBO> response = userController.addUser(user);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());
		verify(userService, times(1)).addUser(user);
	}

	@Test
	public void testLoginSuccess() {
		when(userService.checkUser(user)).thenReturn("Login successful");
		when(userService.getUserId(user)).thenReturn(1L);

		ResponseEntity<Map<String, String>> response = userController.login(user);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login successful", response.getBody().get("message"));
		assertEquals("1", response.getBody().get("userId"));
		verify(userService, times(1)).checkUser(user);
		verify(userService, times(1)).getUserId(user);
	}

	@Test
	public void testLoginFailure() {
		when(userService.checkUser(user)).thenThrow(new IllegalArgumentException("Invalid credentials"));

		ResponseEntity<Map<String, String>> response = userController.login(user);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Invalid credentials", response.getBody().get("error"));
		verify(userService, times(1)).checkUser(user);
		verify(userService, times(0)).getUserId(user);
	}
}
