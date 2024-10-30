package com.portfolio.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private UserBO user;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();

		// Sample user data for testing
		user = new UserBO();
		user.setId(1L);
		user.setEmail("sai@gmail.com");
		user.setUsername("sai");
		user.setPassword("password");
	}

	@Test
	public void testAddUser() throws Exception {
		// Mock service behavior
		Mockito.when(userService.addUser(Mockito.any(UserBO.class))).thenReturn(user);

		// Perform POST request
		mockMvc.perform(post("/users/adduser").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(user.getId())).andExpect(jsonPath("$.email").value(user.getEmail()))
				.andExpect(jsonPath("$.username").value(user.getUsername()));
	}

	@Test
	public void testLogin_Successful() throws Exception {
		// Mock service behavior for successful login
		Mockito.when(userService.checkUser(Mockito.any(UserBO.class))).thenReturn("Login successful");
		Mockito.when(userService.getUserId(Mockito.any(UserBO.class))).thenReturn(1L);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("message", "Login successful");
		expectedResponse.put("userId", "1");

		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Login successful")).andExpect(jsonPath("$.userId").value("1"));
	}

	@Test
	public void testLogin_InvalidCredentials() throws Exception {
		// Mock service behavior for invalid login
		Mockito.when(userService.checkUser(Mockito.any(UserBO.class)))
				.thenThrow(new IllegalArgumentException("Invalid credentials"));

		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid credentials"));
	}

}
