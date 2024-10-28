package com.portfolio.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockService;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "*")
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private PortfolioService portfolioService;

	@PostMapping("/{userId}")
	public ResponseEntity<Void> addStock(@PathVariable Long userId, @RequestBody InsertStockRequest addStockRequest) {
		stockService.addStock(userId, addStockRequest);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{userId}/{stockId}")
	public ResponseEntity<String> deleteStock(@PathVariable Long userId, @PathVariable Long stockId) {
		// First, remove the stock from the portfolio
		portfolioService.removeStockFromPortfolio(userId, stockId);

		// Then, delete the stock
		stockService.deleteStock(stockId, userId);
		return ResponseEntity.noContent().build();
		//return ResponseEntity.ok("Stock and associated portfolio entry deleted successfully");
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<StocksBO>> getAllStocksByUserId(@PathVariable Long userId) {
		List<StocksBO> stocks = stockService.getAllStocksByUserId(userId);
		return ResponseEntity.ok(stocks);
	}

	/*
	 * @GetMapping("/symbol/{stockId}") public String getStockSymbol(@PathVariable
	 * Long stockId) { return stockService.getStockSymbol(stockId); }
	 */

	/*
	 * @GetMapping("/{userId}/{stockId}") public ResponseEntity<List<Stocks>>
	 * getStockInfoByUserId(@PathVariable Long userId,@PathVariable Long stockId) {
	 * List<Stocks> stocks = stockService.getStockInfoByUserId(userId,stockId);
	 * return ResponseEntity.ok(stocks); }
	 */

}
