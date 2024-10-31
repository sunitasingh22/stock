package com.portfolio.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.service.impl.PortfolioServiceImpl;

public class PortfolioServiceUnitTest {

	@Mock
	private PortfolioRepository portfolioRepository;

	@InjectMocks
	private PortfolioServiceImpl portfolioService;

	private PortfolioBO portfolio;

	private UserBO user;

	private StocksBO stock;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		user = new UserBO();
		user.setId(1L);
		user.setUsername("sai");
		user.setEmail("sai@gmail.com");
		user.setPassword("password");

		stock = new StocksBO();
		stock.setId(1L);
		stock.setName("AAPLE");
		stock.setSymbol("AAPL");

		// Initialize test data
		portfolio = new PortfolioBO();
		portfolio.setUser(user);
		portfolio.setStock(stock);
	}

	@Test
	public void testRemoveStockFromPortfolio() {
		// Act
		portfolioService.removeStockFromPortfolio(1L, 101L);

		// Verify deleteByUserIdAndStockId was called with correct arguments
		verify(portfolioRepository, times(1)).deleteByUserIdAndStockId(1L, 101L);
	}

	@Test
	public void testGetPortfolioByUserId() {
		// Mock repository to return a list of portfolios
		when(portfolioRepository.findByUserId(anyLong())).thenReturn(List.of(portfolio));

		// Act
		List<PortfolioBO> portfolios = portfolioService.getPortfolioByUserId(1L);

		// Assert
		assertThat(portfolios).isNotEmpty();
		assertThat(portfolios.get(0).getUser().getId()).isEqualTo(1L);
		verify(portfolioRepository, times(1)).findByUserId(1L);
	}

	@Test
	public void testGetPortfolio() {
		// Mock repository to return a single portfolio entry
		when(portfolioRepository.findByUserIdAndStockId(anyLong(), anyLong())).thenReturn(portfolio);

		// Act
		PortfolioBO portfolio = portfolioService.getPortfolioByUserIdAndStockId(1L, 101L);

		// Assert
		assertThat(portfolio).isNotNull();
		assertThat(portfolio.getUser().getId()).isEqualTo(1L);
		assertThat(portfolio.getStock().getId()).isEqualTo(1L);
		verify(portfolioRepository, times(1)).findByUserIdAndStockId(1L, 101L);
	}

}
