package com.portfolio.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.dto.StockList;
import com.portfolio.management.mapper.StockListMapper;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockListService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stocks")
@Slf4j
public class StockController {

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private StockListService stockListService;

	@GetMapping("/{userId}")
	public ResponseEntity<List<StockList>> getAllStocksByUserId(@PathVariable Long userId) {
		log.info("Getting all stocks for userId: {}", userId);
		List<StockListBO> stockListBO = stockListService.getAllStocksByUserId(userId);
		// convert BO object to dto for response entity
		List<StockList> stocksList = StockListMapper.INSTANCE.toDtoList(stockListBO);
		if (stocksList.isEmpty()) {
			log.warn("Stocks not found for userId: {}", userId);
		} else {
			log.info("Found {} stocks for userId: {}", stocksList.size(), userId);
		}
		return ResponseEntity.ok(stocksList);
	}

	@PostMapping("/{userId}/{stockId}")
	public ResponseEntity<Void> addStockRequest(@PathVariable Long userId, @PathVariable Long stockId,
			@RequestBody InsertStockRequest addStockRequest) {
		log.info("Request to add stocks for userId: {}", userId);
		portfolioService.addStockRequest(userId, stockId, addStockRequest);
		log.info("Stock(s) added successfully for userId: {}", userId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{userId}/{stockId}/{quantity}")
	public ResponseEntity<String> removeStock(@PathVariable Long userId, @PathVariable Long stockId,
			@PathVariable Integer quantity) {
		log.info("Request to remove stocks with stockId: {} for userId: {}", stockId, userId);
		portfolioService.removeStockFromPortfolio(userId, stockId, quantity);
		log.info("Stock(s) deleted successfully for userId: {}", stockId, userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/all")
	public List<StockList> getAllStocks() {
		log.info("Getting list of all stocks");
		List<StockListBO> allStocksList = stockListService.getAllStocks();
		if (allStocksList.isEmpty()) {
			log.warn("Stock List not fetched from database");
		} else {
			log.info("Received stocks list from database");
		}
		// convert BO object to dto for response entity
		List<StockList> StockListdto = StockListMapper.INSTANCE.toDtoList(allStocksList);
		return StockListdto;
	}

}
