package com.portfolio.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.management.model.StocksBO;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockRepositoryUnitTest {
	
	@Autowired
    private StockRepository stockRepository;

    private StocksBO stock;

    @BeforeEach
    public void setUp() {
        stock = new StocksBO();
        stock.setId(1L);
        stock.setSymbol("AAPL");
        stockRepository.save(stock);
    }

    @Test
    public void testFindBySymbol() {
        Optional<StocksBO> stockFromDB = stockRepository.findBySymbol(stock.getSymbol());

        assertThat(stockFromDB).isPresent();
        assertThat(stockFromDB.get().getSymbol()).isEqualTo(stock.getSymbol());
    }

    @Test
    public void testFindBySymbolNotFound() {
        Optional<StocksBO> stockFromDB = stockRepository.findBySymbol("UNKNOWN");

        assertThat(stockFromDB).isNotPresent();
    }

}
