package com.portfolio.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = "*")
public class PortfolioController {
	
	@Autowired 
	private PortfolioService portfolioService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<PortfolioBO>> getPortfolioByUserId(@PathVariable Long userId) {
	    List<PortfolioBO> portfolio = portfolioService.getPortfolioByUserId(userId);
	     return ResponseEntity.ok(portfolio);
	}
	
	/*
	 * @GetMapping("/{userId}/{stockId}") public ResponseEntity<Portfolio>
	 * getPortfolio(@PathVariable Long userId,@PathVariable Long stockId) {
	 * 
	 * Portfolio portfolio = portfolioService.getPortfolio(userId, stockId); if
	 * (portfolio != null) { return ResponseEntity.ok(portfolio); } else { return
	 * ResponseEntity.notFound().build(); } }
	 */
    
}
