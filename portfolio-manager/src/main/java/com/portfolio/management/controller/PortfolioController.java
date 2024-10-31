package com.portfolio.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.management.dto.Portfolio;
import com.portfolio.management.mapper.PortfolioMapper;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.service.PortfolioService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = "*")
@Slf4j
public class PortfolioController {
	
	@Autowired 
	private PortfolioService portfolioService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Portfolio>> getPortfolioByUserId(@PathVariable Long userId) {
		log.info("Fetching portfolio details like stocks, quantity for userId: {}", userId);
	    List<PortfolioBO> portfolio = portfolioService.getPortfolioByUserId(userId);
	    List<Portfolio> portfolioList = PortfolioMapper.INSTANCE.toDtoList(portfolio);
	    if (portfolioList.isEmpty()) {
	        log.warn("Portfolio details not found for userId: {}", userId);
	    } else {
	        log.info("Portfolio details found successfully for userId: {}", userId);
	    }
	     return ResponseEntity.ok(portfolioList);
	}
	    
}
