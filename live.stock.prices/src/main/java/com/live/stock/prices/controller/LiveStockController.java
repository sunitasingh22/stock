package com.live.stock.prices.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.live.stock.prices.model.LiveStockPrice;
import com.live.stock.prices.service.LiveStockPriceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class LiveStockController {

	private final LiveStockPriceService stockPriceService;

	@GetMapping("/price/{symbol}")
	public Mono<LiveStockPrice> getStockPrice(@PathVariable String symbol) {
	log.info("Getting live stock price for given symbol: {}", symbol);
		Mono<LiveStockPrice> stockPrice =  stockPriceService.getStockPrice(symbol);
	if (stockPrice != null) {
		log.info("Stock price for symbol {}: ${}", symbol, stockPrice);
	} else {
		log.warn("Stock price not available symbol: {}", symbol);
	}
		return stockPrice;
	}


}
