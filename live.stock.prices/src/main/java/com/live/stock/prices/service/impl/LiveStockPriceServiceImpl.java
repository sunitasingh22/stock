package com.live.stock.prices.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.live.stock.prices.model.LiveStockPrice;
import com.live.stock.prices.service.LiveStockPriceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor // Lombok
@Slf4j
public class LiveStockPriceServiceImpl implements LiveStockPriceService{

	@Value("${stock.api.url}")
	private String apiUrl;

	@Value("${stock.api.key}")
	private String apiKey;

	private final RestTemplate restTemplate;

	private final WebClient webClient;

	public Mono<LiveStockPrice> getStockPrice(String symbol) {
		String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=1min&apikey=%s", apiUrl, symbol,
				apiKey);

		log.info("Getting stock price for symbol: {}", symbol);

		return webClient.get().uri(url).retrieve().bodyToMono(JsonNode.class) // Get response as JsonNode
				.map(response -> {
					JsonNode timeSeries = response.path("Time Series (1min)");
					String latestTimestamp = timeSeries.fieldNames().next();
					JsonNode latestData = timeSeries.path(latestTimestamp);

					LiveStockPrice stockPrice = new LiveStockPrice();
					stockPrice.setSymbol(symbol);
					stockPrice.setPrice(latestData.path("1. open").asText());
					stockPrice.setTimestamp(latestTimestamp);
					log.info("Fetched stock price: {} at {}", stockPrice.getPrice(), stockPrice.getTimestamp());

					return stockPrice;
				}).onErrorResume(e -> {
					log.error("Error fetching stock price: {}", e.getMessage());
					return Mono.just(new LiveStockPrice()); // Return an empty LiveStockPrice or handle accordingly
				});
	}

	public LiveStockPrice getStockPriceFromRestTemplate(String symbol) {
		// String url = apiUrl + "?function=TIME_SERIES_INTRADAY&symbol=" + symbol +
		// "&interval=1min&apikey=" + apiKey;
		// log.info("Fetching stock price for symbol: {}", symbol);

		// JsonNode response = restTemplate.getForObject(url, JsonNode.class);
		// JsonNode timeSeries = response.path("Time Series (1min)");
		// String latestTimestamp = timeSeries.fieldNames().next();
		// JsonNode latestData = timeSeries.path(latestTimestamp);

		LiveStockPrice stockPrice = new LiveStockPrice();
		// stockPrice.setSymbol(symbol);
		stockPrice.setSymbol("AAPL");
		// stockPrice.setPrice(latestData.path("1. open").asText());
		stockPrice.setPrice("1500");
		// stockPrice.setTimestamp(latestTimestamp);
		stockPrice.setTimestamp("2024-10-24");

		// log.info("Fetched stock price: {} at {}", stockPrice.getPrice(),
		// stockPrice.getTimestamp());

		return stockPrice;
	}
}
