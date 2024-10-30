package com.portfolio.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.service.PortfolioService;

public class PortfolioControllerUnitTest {

	@InjectMocks
	private PortfolioController portfolioController;

	@Mock
	private PortfolioService portfolioService;

	private Long userId;
	private List<PortfolioBO> mockPortfolioList;

	@BeforeEach
	private void setUp() {
		MockitoAnnotations.openMocks(this);
		userId = 1L;
		mockPortfolioList = Arrays.asList(new PortfolioBO(), new PortfolioBO());
	}

	@Test
	public void testGetPortfolioByUserId() {

		when(portfolioService.getPortfolioByUserId(userId)).thenReturn(mockPortfolioList);

		ResponseEntity<List<PortfolioBO>> responseObject = portfolioController.getPortfolioByUserId(userId);

		assertEquals(HttpStatus.OK, responseObject.getStatusCode());
		assertEquals(mockPortfolioList, responseObject.getBody());
		verify(portfolioService, times(1)).getPortfolioByUserId(userId);

	}

}
