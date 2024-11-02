package com.portfolio.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PortfolioRepositoryUnitTest {
	
	@Autowired
    private PortfolioRepository portfolioRepository;
	
	@Autowired
    private UserRepository userRepository;  
	
    @Autowired
    private StockListRepository stocksRepository;

    private UserBO user;
    private StockListBO stock;
    private PortfolioBO portfolio;

    @BeforeEach
    public void setUp() {
        user = new UserBO();
       // user.setId(1L);
        user.setEmail("sai@gmail.com");
        user.setUsername("sai");
        user.setPassword("123123");
        user = userRepository.save(user);

        stock = new StockListBO();
        //stock.setId(2L);
        stock.setSymbol("AAPL");
        stock.setName("Apple inc.");
        stock = stocksRepository.save(stock);

        portfolio = new PortfolioBO();
       // portfolio.setId(1L);
        portfolio.setUser(user);
        portfolio.setStock(stock);
        portfolio.setQuantity(10);
        portfolioRepository.save(portfolio);
    }

    @Test
    public void testFindByUserIdAndStockId() {
        PortfolioBO foundPortfolio = portfolioRepository.findByUserIdAndStockId(user.getId(), stock.getId());
        assertThat(foundPortfolio).isNotNull();
        assertThat(foundPortfolio.getUser().getId()).isEqualTo(user.getId());
        assertThat(foundPortfolio.getStock().getId()).isEqualTo(stock.getId());
    }

    @Test
    public void testFindByUserId() {
        List<PortfolioBO> foundPortfolios = portfolioRepository.findByUserId(user.getId());
        assertThat(foundPortfolios).isNotEmpty();
        assertThat(foundPortfolios.size()).isEqualTo(1);
        assertThat(foundPortfolios.get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testDeleteByUserIdAndStockId() {
        portfolioRepository.deleteByUserIdAndStockId(user.getId(), stock.getId());
        PortfolioBO deletedPortfolio = portfolioRepository.findByUserIdAndStockId(user.getId(), stock.getId());
        assertThat(deletedPortfolio).isNull();
    }
}
