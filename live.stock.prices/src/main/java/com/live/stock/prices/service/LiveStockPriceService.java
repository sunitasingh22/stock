package com.live.stock.prices.service;

import com.live.stock.prices.model.LiveStockPrice;

import reactor.core.publisher.Mono;

public interface LiveStockPriceService {

	public Mono<LiveStockPrice> getStockPrice(String symbol);

	//public LiveStockPrice getStockPriceFromRestTemplate(String symbol);
}
