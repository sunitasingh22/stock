package com.live.stock.prices.model;

import lombok.Data;

@Data
public class LiveStockPrice {

	private String symbol;
	private String price;
	private String timestamp;

}
