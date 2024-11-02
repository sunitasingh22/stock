package com.portfolio.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        ResponseEntity<Void> response = userController.addUser(user);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).addUser(userObj);
    }

    @Test
    public void testLoginSuccess() {
        UserBO userObj = UserMapper.INSTANCE.userDtoToBO(user);
        when(userService.checkUser(userObj)).thenReturn(userObj);

        ResponseEntity<Users> response = userController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        assertEquals(user.getEmail(), response.getBody().getEmail());
        verify(userService, times(1)).checkUser(userObj);
    }

    @Test
    public void testLoginFailure() {
    	 // Mock the behavior of UserService to throw an exception
        UserBO userBO = UserMapper.INSTANCE.userDtoToBO(user);
        when(userService.checkUser(userBO)).thenThrow(new IllegalArgumentException("Invalid credentials"));

        // Call the login method and assert the exception is handled
        try {
            userController.login(user);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid credentials", e.getMessage());
            verify(userService).checkUser(userBO);
        }
    }
}
