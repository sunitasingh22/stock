package com.live.stock.prices.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.live.stock.prices.model.LiveStockPrice;
import com.live.stock.prices.service.LiveStockPriceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LiveStockController {

	private final LiveStockPriceService stockPriceService;

//	@GetMapping("/price/{symbol}")
//	public Mono<LiveStockPrice> getStockPrice(@PathVariable String symbol) {
//		return stockPriceService.getStockPrice(symbol);
//	}
	
	@GetMapping("/price/{symbol}")
	public LiveStockPrice getStockPriceFromRest(@PathVariable String symbol) {
		return stockPriceService.getStockPriceFromRestTemplate(symbol);
	}
}
