package com.portfolio.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.StockList;
import com.portfolio.management.model.StockListBO;

public class StockListMapperTest {
	
	private final StockListMapper stockListMapper = Mappers.getMapper(StockListMapper.class);

    @Test
    public void testToDtoListMapping() {

    	StockListBO stockBO1 = new StockListBO();
        stockBO1.setId(1L);
        stockBO1.setName("Apple Inc");
        stockBO1.setSymbol("AAPL");

        StockListBO stockBO2 = new StockListBO();
        stockBO2.setId(2L);
        stockBO2.setName("Google LLC");
        stockBO2.setSymbol("GOOGL");

        List<StockListBO> stockBOList = Arrays.asList(stockBO1, stockBO2);

        // Perform mapping
        List<StockList> stockListDTOs = stockListMapper.toDtoList(stockBOList);

        // Assert mappings
        assertThat(stockListDTOs).isNotNull();
        assertThat(stockListDTOs).hasSize(2);
        assertThat(Long.valueOf(stockListDTOs.get(0).getId())).isEqualTo(stockBO1.getId());
        assertThat(Long.valueOf(stockListDTOs.get(1).getId())).isEqualTo(stockBO2.getId());
        assertThat(stockListDTOs.get(0).getName()).isEqualTo(stockBO1.getName());
        assertThat(stockListDTOs.get(1).getName()).isEqualTo(stockBO2.getName());
        assertThat(stockListDTOs.get(0).getSymbol()).isEqualTo(stockBO1.getSymbol());
        assertThat(stockListDTOs.get(1).getSymbol()).isEqualTo(stockBO2.getSymbol());
    }

}
