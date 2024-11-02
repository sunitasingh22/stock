package com.portfolio.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.portfolio.management.dto.Portfolio;
import com.portfolio.management.dto.StockList;
import com.portfolio.management.dto.Users;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;

public class PortfolioMapperTest {

    private final PortfolioMapper portfolioMapper = PortfolioMapper.INSTANCE;

    @Test
    public void testToDtoListReturnsPortfolioList() {

    	UserBO user = new UserBO();
        user.setId(1L);
        
        StockListBO stock = new StockListBO();
        stock.setId(1L);
        stock.setSymbol("AAPL");
        stock.setName("Apple Inc.");

        PortfolioBO portfolioBO1 = new PortfolioBO();
        portfolioBO1.setId(1L);
        portfolioBO1.setUser(user);
        portfolioBO1.setStock(stock);
        portfolioBO1.setQuantity(100);
        portfolioBO1.setAddedAt(new Date());

        PortfolioBO portfolioBO2 = new PortfolioBO();
        portfolioBO2.setId(2L);
        portfolioBO2.setUser(user);
        portfolioBO2.setStock(stock);
        portfolioBO2.setQuantity(50);
        portfolioBO2.setAddedAt(new Date());

        List<PortfolioBO> portfolioBOList = Arrays.asList(portfolioBO1, portfolioBO2);

        List<Portfolio> portfolioList = portfolioMapper.toDtoList(portfolioBOList);

        assertThat(portfolioList).hasSize(2);
        assertThat(portfolioList.get(0).getStock().getSymbol()).isEqualTo("AAPL");
        assertThat(portfolioList.get(1).getStock().getSymbol()).isEqualTo("AAPL");
    }

    @Test
    public void testToDtoListReturnsEmptyList() {
        List<Portfolio> portfolioList = portfolioMapper.toDtoList(Collections.emptyList());
        assertThat(portfolioList).isEmpty();
    }

    @Test
    public void testToBOListReturnsPortfolioBOList() {
        Users user = new Users();
        user.setId(1);
        
        StockList stock = new StockList();
        stock.setId(1);
        stock.setSymbol("AAPL");
        stock.setName("Apple Inc.");

        Portfolio portfolio1 = new Portfolio();
        portfolio1.setId(1);
        portfolio1.setUser(user);
        portfolio1.setStock(stock);
        portfolio1.setQuantity(100);

        Portfolio portfolio2 = new Portfolio();
        portfolio2.setId(2);
        portfolio2.setUser(user);
        portfolio2.setStock(stock);
        portfolio2.setQuantity(50);

        List<Portfolio> portfolioList = Arrays.asList(portfolio1, portfolio2);

        List<PortfolioBO> portfolioBOList = portfolioMapper.toBOList(portfolioList);

        assertThat(portfolioBOList).hasSize(2);
        assertThat(portfolioBOList.get(0).getStock().getSymbol()).isEqualTo("AAPL");
        assertThat(portfolioBOList.get(1).getStock().getSymbol()).isEqualTo("AAPL");
    }

    @Test
    public void testToBOList_WithEmptyList_ReturnsEmptyList() {
        List<PortfolioBO> portfolioBOList = portfolioMapper.toBOList(Collections.emptyList());
        assertThat(portfolioBOList).isEmpty();
    }

    @Test
    public void testPortfolioBOToDtoReturnsPortfolio() {
        UserBO user = new UserBO();
        user.setId(1L);
        
        StockListBO stock = new StockListBO();
        stock.setId(1L);
        stock.setSymbol("AAPL");
        stock.setName("Apple Inc.");

        PortfolioBO portfolioBO = new PortfolioBO();
        portfolioBO.setId(1L);
        portfolioBO.setUser(user);
        portfolioBO.setStock(stock);
        portfolioBO.setQuantity(100);
        portfolioBO.setAddedAt(new Date());

        Portfolio portfolio = portfolioMapper.portfolioBOToDto(portfolioBO);

        assertThat(portfolio).isNotNull();
        assertThat(portfolio.getQuantity()).isEqualTo(100);
    }

    @Test
    public void testPortfolioDtoToBOReturnsPortfolioBO() {
        Users user = new Users();
        user.setId(1);
        
        StockList stock = new StockList();
        stock.setId(1);
        stock.setSymbol("AAPL");
        stock.setName("Apple Inc.");

        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        portfolio.setUser(user);
        portfolio.setStock(stock);
        portfolio.setQuantity(100);

        PortfolioBO portfolioBO = portfolioMapper.portfolioDtoToBO(portfolio);

        assertThat(portfolioBO).isNotNull();
        assertThat(portfolioBO.getStock().getSymbol()).isEqualTo("AAPL");
        assertThat(portfolioBO.getQuantity()).isEqualTo(100);
    }
}
