package com.portfolio.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.management.model.StockListBO;
import com.portfolio.management.repository.StockListRepository;
import com.portfolio.management.service.StockListService;

@Service
public class StockListServiceImpl implements StockListService{
	
	private StockListRepository stockListRepository;

    @Autowired
    public StockListServiceImpl(StockListRepository stockListRepository) {
        this.stockListRepository = stockListRepository;
    }
	

	@Override
	public List<StockListBO> getAllStocks() {
		 return stockListRepository.findAll();
	}

}
