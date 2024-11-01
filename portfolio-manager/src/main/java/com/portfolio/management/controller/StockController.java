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
import com.portfolio.management.dto.StockList;
import com.portfolio.management.dto.Stocks;
import com.portfolio.management.mapper.StockListMapper;
import com.portfolio.management.mapper.StockMapper;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockListService;
import com.portfolio.management.service.StockService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "*")
@Slf4j
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private StockListService stockListService;

	@PostMapping("/{userId}")
	public ResponseEntity<Void> addStock(@PathVariable Long userId, @RequestBody InsertStockRequest addStockRequest) {
		log.info("Request to add stock for userId: {}", userId);
		stockService.addStock(userId, addStockRequest);
		log.info("Stock added successfully for userId: {}", userId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{userId}/{stockId}")
	public ResponseEntity<String> deleteStock(@PathVariable Long userId, @PathVariable Long stockId) {
		log.info("Request to delete stock with stockId: {} for userId: {}", stockId, userId);
		portfolioService.removeStockFromPortfolio(userId, stockId);
		log.info("Stock deleted successfully for userId: {}", stockId, userId);
		stockService.deleteStock(stockId, userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Stocks>> getAllStocksByUserId(@PathVariable Long userId) {
		log.info("Getting all stocks for userId: {}", userId);
		List<StocksBO> stockBo = stockService.getAllStocksByUserId(userId);
		//convert BO object to dto for response entity
		List<Stocks> stocksList = StockMapper.INSTANCE.toDtoList(stockBo);
		if (stocksList.isEmpty()) {
			log.warn("Stocks not found for userId: {}", userId);
		} else {
			log.info("Found {} stocks for userId: {}", stocksList.size(), userId);
		}
		return ResponseEntity.ok(stocksList);
	}

	@GetMapping("/all")
	public List<StockList> getAllStocks() {
		log.info("Getting list of all stocks");
		List<StockListBO> allStocksList = stockListService.getAllStocks();
		if(allStocksList.isEmpty()) {
			log.warn("Stock List not fetched from database");
		}else {
			log.info("Received stocks list from database");
		}
		//convert BO object to dto for response entity
		List<StockList> StockListdto = StockListMapper.INSTANCE.toDtoList(allStocksList);
		return StockListdto;
	}

}
