package com.live.stock.prices.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.live.stock.prices.model.LiveStockPrice;
import com.live.stock.prices.service.LiveStockPriceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class LiveStockController {

	private final LiveStockPriceService stockPriceService;
// dont delete
//	@GetMapping("/price/{symbol}")
//	public Mono<LiveStockPrice> getStockPrice(@PathVariable String symbol) {
//	log.info("Getting live stock price for given symbol: {}", symbol);
//		LiveStockPrice stockPrice =  stockPriceService.getStockPrice(symbol);
//	if (stockPrice != null) {
//		log.info("Stock price for symbol {}: ${}", symbol, stockPrice.getPrice());
//	} else {
//		log.warn("Stock price not available symbol: {}", symbol);
//	}
//		return stockPrice;
//	}

	@GetMapping("/price/{symbol}")
	public LiveStockPrice getStockPriceFromRest(@PathVariable String symbol) {
		log.info("Getting live stock price for given symbol: {}", symbol);
		LiveStockPrice stockPrice = stockPriceService.getStockPriceFromRestTemplate(symbol);
		if (stockPrice != null) {
			log.info("Stock price for symbol {}: ${}", symbol, stockPrice.getPrice());
		} else {
			log.warn("Stock price not available symbol: {}", symbol);
		}
		return stockPrice;
	}
}
