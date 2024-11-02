package com.portfolio.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.portfolio.management.dto.Users;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private Users user;
	private UserBO userBO;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();

		// Sample user data for testing
		user = new Users();
		user.setId(1);
		user.setEmail("sai@gmail.com");
		user.setUsername("sai");
		user.setPassword("password");

		// Create a sample UserBO for mock service return
		userBO = new UserBO();
		userBO.setId(1L);
		userBO.setUsername("sai");
		userBO.setEmail("sai@gmail.com");

	}

	@Test
	public void testAddUser() throws Exception {
		// Mock service behavior
		UserBO userBO = new UserBO(); // Assuming UserBO is the entity used in the service
		userBO.setId(1L);
		userBO.setEmail(user.getEmail());
		userBO.setUsername(user.getUsername());
		userBO.setPassword(user.getPassword());

		Mockito.when(userService.addUser(Mockito.any(UserBO.class))).thenReturn(userBO);

		// Perform POST request
		mockMvc.perform(post("/users/adduser").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isNoContent());
	}

	@Test
	public void testLogin_Successful() throws Exception {
		// Mock the service layer to return a verified UserBO
		Mockito.when(userService.checkUser(Mockito.any(UserBO.class))).thenReturn(userBO);

		// Perform POST request to /users/login
		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.username").value(user.getUsername()))
				.andExpect(jsonPath("$.email").value(user.getEmail()));
	}

}
