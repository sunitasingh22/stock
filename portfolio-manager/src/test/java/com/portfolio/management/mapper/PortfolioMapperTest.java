package com.portfolio.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.Portfolio;
import com.portfolio.management.dto.Stocks;
import com.portfolio.management.dto.Users;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;

public class PortfolioMapperTest {
	private final PortfolioMapper portfolioMapper = Mappers.getMapper(PortfolioMapper.class);

    @Test
    public void testToDtoMapping() {
        // Create sample data for PortfolioBO
        PortfolioBO portfolioBO = new PortfolioBO();
        portfolioBO.setId(1L);
        portfolioBO.setQuantity(100);
        portfolioBO.setAddedAt(new Date());
        
        UserBO user = new UserBO();
        user.setId(1L);
        user.setUsername("sai");
        user.setEmail("sai@gmail.com");
        user.setPassword("sai123");
        portfolioBO.setUser(user);

        StockListBO stock = new StockListBO();
        stock.setId(1L);
        stock.setSymbol("AAPL");
        stock.setName("Apple Inc");
        portfolioBO.setStock(stock);

        // Perform mapping
        Portfolio portfolioDTO = portfolioMapper.portfolioBOToDto(portfolioBO);

        // Assert mappings
        assertThat(portfolioDTO).isNotNull();
        assertThat(Long.valueOf(portfolioDTO.getId())).isEqualTo(portfolioBO.getId());
        assertThat(portfolioDTO.getQuantity()).isEqualTo(portfolioBO.getQuantity());
        assertThat(Long.valueOf(portfolioDTO.getUser().getId())).isEqualTo(portfolioBO.getUser().getId());
        assertThat(Long.valueOf(portfolioDTO.getStock().getId())).isEqualTo(portfolioBO.getStock().getId());
    }

    @Test
    public void testToEntityMapping() {
    	 Users user = new Users();
         user.setId(1);
         user.setUsername("sai");
         user.setEmail("sai@gmail.com");
         user.setPassword("sai123");
         
         Stocks stock = new Stocks();
         stock.setId(1);
         stock.setSymbol("AAPL");
         stock.setName("Apple Inc");
    	
        Portfolio portfolioDTO = new Portfolio();
        portfolioDTO.setId(1);
        portfolioDTO.setQuantity(100);
        portfolioDTO.setUser(user);
        portfolioDTO.setStock(stock);

        // Perform mapping
        PortfolioBO portfolioBO = portfolioMapper.portfolioDtoToBO(portfolioDTO);

        // Assert mappings
        assertThat(portfolioBO).isNotNull();
        assertThat(portfolioBO.getId()).isEqualTo(Long.valueOf(portfolioDTO.getId()));
        assertThat(portfolioBO.getQuantity()).isEqualTo(portfolioDTO.getQuantity());
        assertThat(portfolioBO.getUser().getId()).isEqualTo(Long.valueOf(portfolioDTO.getUser().getId()));
        assertThat(portfolioBO.getStock().getId()).isEqualTo(Long.valueOf(portfolioDTO.getStock().getId()));
    }
    
    @Test
    public void testToDtoListMapping() {
        PortfolioBO portfolioBO1 = new PortfolioBO();
        portfolioBO1.setId(1L);
        portfolioBO1.setQuantity(100);
        portfolioBO1.setAddedAt(new Date());

        UserBO user1 = new UserBO();
        user1.setId(1L);
        user1.setUsername("sai");
        user1.setEmail("sai@gmail.com");
        user1.setPassword("sai123");
        portfolioBO1.setUser(user1);

        StockListBO stock1 = new StockListBO();
        stock1.setId(1L);
        stock1.setSymbol("AAPL");
        portfolioBO1.setStock(stock1);

        PortfolioBO portfolioBO2 = new PortfolioBO();
        portfolioBO2.setId(2L);
        portfolioBO2.setQuantity(200);
        portfolioBO2.setAddedAt(new Date());

        UserBO user2 = new UserBO();
        user2.setId(2L);
        user2.setUsername("ram");
        portfolioBO2.setUser(user2);

        StockListBO stock2 = new StockListBO();
        stock2.setId(2L);
        stock2.setSymbol("GOOGL");
        portfolioBO2.setStock(stock2);

        List<PortfolioBO> portfolioBOList = Arrays.asList(portfolioBO1, portfolioBO2);

        // Perform mapping
        List<Portfolio> portfolioDTOList = portfolioMapper.toDtoList(portfolioBOList);

        // Assert mappings
        assertThat(portfolioDTOList).isNotNull();
        assertThat(portfolioDTOList).hasSize(2);
        assertThat(Long.valueOf(portfolioDTOList.get(0).getId())).isEqualTo(portfolioBO1.getId());
        assertThat(Long.valueOf(portfolioDTOList.get(1).getId())).isEqualTo(portfolioBO2.getId());
    }

    @Test
    public void testToBOListMapping() {
        // Create sample data for Portfolio list
        Users user1 = new Users();
        user1.setId(1);
        user1.setUsername("sai");
        user1.setEmail("sai@gmail.com");

        Stocks stock1 = new Stocks();
        stock1.setId(1);
        stock1.setSymbol("AAPL");

        Portfolio portfolioDTO1 = new Portfolio();
        portfolioDTO1.setId(1);
        portfolioDTO1.setQuantity(100);
        portfolioDTO1.setUser(user1);
        portfolioDTO1.setStock(stock1);

        Users user2 = new Users();
        user2.setId(2);
        user2.setUsername("ram");

        Stocks stock2 = new Stocks();
        stock2.setId(2);
        stock2.setSymbol("GOOGL");

        Portfolio portfolioDTO2 = new Portfolio();
        portfolioDTO2.setId(2);
        portfolioDTO2.setQuantity(200);
        portfolioDTO2.setUser(user2);
        portfolioDTO2.setStock(stock2);

        List<Portfolio> portfolioDTOList = Arrays.asList(portfolioDTO1, portfolioDTO2);

        // Perform mapping
        List<PortfolioBO> portfolioBOList = portfolioMapper.toBOList(portfolioDTOList);

        // Assert mappings
        assertThat(portfolioBOList).isNotNull();
        assertThat(portfolioBOList).hasSize(2);
        assertThat(portfolioBOList.get(0).getId()).isEqualTo(Long.valueOf(portfolioDTO1.getId()));
        assertThat(portfolioBOList.get(1).getId()).isEqualTo(Long.valueOf(portfolioDTO2.getId()));
    }
    
    

}
