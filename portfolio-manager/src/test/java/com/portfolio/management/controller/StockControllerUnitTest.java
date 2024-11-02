package com.portfolio.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.portfolio.management.dto.StockList;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockListService;

public class StockControllerUnitTest {

	@InjectMocks
	private StockController stockController;

	@Mock
	private StockListService stockListService;

	@Mock
	private PortfolioService portfolioService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		new InsertStockRequest();
		Arrays.asList(new StockList(), new StockList());
	}

	@Test
	public void testAddStockRequest() {
		Long userId = 1L;
		Long stockId = 10L;
		InsertStockRequest addStockRequest = new InsertStockRequest();

		ResponseEntity<Void> response = stockController.addStockRequest(userId, stockId, addStockRequest);

		verify(portfolioService, times(1)).addStockRequest(userId, stockId, addStockRequest);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testRemoveStock() {
		Long userId = 1L;
		Long stockId = 10L;
		Integer quantity = 5;

		ResponseEntity<String> response = stockController.removeStock(userId, stockId, quantity);

		verify(portfolioService, times(1)).removeStockFromPortfolio(userId, stockId, quantity);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testGetAllStocksByUserId() {
		Long userId = 1L;
	    
	    when(stockListService.getAllStocksByUserId(userId)).thenReturn(new ArrayList<>());

	    ResponseEntity<List<StockList>> response = stockController.getAllStocksByUserId(userId);

	    verify(stockListService, times(1)).getAllStocksByUserId(userId);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(response.getBody()); 
	    assertEquals(0, response.getBody().size());
    }

}
