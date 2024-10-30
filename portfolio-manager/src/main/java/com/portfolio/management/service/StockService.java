package com.portfolio.management.service;

import java.util.List;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.model.StocksBO;

public interface StockService {

	public StocksBO addStock(Long userId, InsertStockRequest addStockRequest);

	public void deleteStock(Long stockId, Long userId);

	//public List<StocksBO> getAllStocks();

	public List<StocksBO> getAllStocksByUserId(Long userId);

	//public List<StocksBO> getStockInfoByUserId(Long userId, Long stockId);

	public String getStockSymbol(Long stockId);

}
