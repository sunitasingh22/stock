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

import com.portfolio.management.dto.Portfolio;
import com.portfolio.management.mapper.PortfolioMapper;
import com.portfolio.management.mapper.StockMapper;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;

public class PortfolioControllerUnitTest {

	@InjectMocks
	private PortfolioController portfolioController;

	@Mock
	private PortfolioService portfolioService;

	private Long userId;
	private List<Portfolio> mockPortfolioList;

	@BeforeEach
	private void setUp() {
		MockitoAnnotations.openMocks(this);
		userId = 1L;
		mockPortfolioList = Arrays.asList(new Portfolio(), new Portfolio());
	}

	@Test
	public void testGetPortfolioByUserId() {

		List<PortfolioBO> portfolioBOList = PortfolioMapper.INSTANCE.toBOList(mockPortfolioList);
		when(portfolioService.getPortfolioByUserId(userId)).thenReturn(portfolioBOList);

		ResponseEntity<List<Portfolio>> responseObject = portfolioController.getPortfolioByUserId(userId);

		assertEquals(HttpStatus.OK, responseObject.getStatusCode());
		assertEquals(mockPortfolioList, responseObject.getBody());
		verify(portfolioService, times(1)).getPortfolioByUserId(userId);

	}

}
