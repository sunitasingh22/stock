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

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.dto.Stocks;
import com.portfolio.management.mapper.StockMapper;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockService;

public class StockControllerUnitTest {

	@InjectMocks
	private StockController stockController;

	@Mock
	private StockService stockService;

	@Mock
	private PortfolioService portfolioService;

	private Long userId;
	private Long stockId;
	private InsertStockRequest addStockRequest;
	private List<Stocks> stockList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		userId = 1L;
		stockId = 1L;
		addStockRequest = new InsertStockRequest();
		stockList = Arrays.asList(new Stocks(), new Stocks());
	}

	@Test
	public void testAddStock() {
		ResponseEntity<Void> response = stockController.addStock(userId, addStockRequest);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(stockService, times(1)).addStock(userId, addStockRequest);
	}

	@Test
	public void testDeleteStock() {
		ResponseEntity<String> response = stockController.deleteStock(userId, stockId);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(portfolioService, times(1)).removeStockFromPortfolio(userId, stockId);
		verify(stockService, times(1)).deleteStock(stockId, userId);
	}

	@Test
	public void testGetAllStocksByUserId() {
		List<StocksBO> StockBOList = StockMapper.INSTANCE.toBOList(stockList);
		when(stockService.getAllStocksByUserId(userId)).thenReturn(StockBOList);

		ResponseEntity<List<Stocks>> response = stockController.getAllStocksByUserId(userId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(stockList, response.getBody());
		verify(stockService, times(1)).getAllStocksByUserId(userId);
	}

}
