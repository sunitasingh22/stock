package com.portfolio.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.Stocks;
import com.portfolio.management.model.StocksBO;

public class StockMapperTest {
	
	private final StockMapper stockMapper = Mappers.getMapper(StockMapper.class);

    @Test
    public void testStockDtoToBO() {

    	Stocks stockDTO = new Stocks();
        stockDTO.setId(1);
        stockDTO.setSymbol("AAPL");
        stockDTO.setName("Apple Inc");

        // Perform mapping
        StocksBO stocksBO = stockMapper.stockDtoToBO(stockDTO);

        // Assert mappings
        assertThat(stocksBO).isNotNull();
        assertThat(stocksBO.getId()).isEqualTo(Long.valueOf(stockDTO.getId()));
        assertThat(stocksBO.getSymbol()).isEqualTo(stockDTO.getSymbol());
        assertThat(stocksBO.getName()).isEqualTo(stockDTO.getName());
    }

    @Test
    public void testStockBOToDto() {

    	StocksBO stocksBO = new StocksBO();
        stocksBO.setId(1L);
        stocksBO.setSymbol("AAPL");
        stocksBO.setName("Apple Inc");

        // Perform mapping
        Stocks stockDTO = stockMapper.stockBOToDto(stocksBO);

        // Assert mappings
        assertThat(stockDTO).isNotNull();
        assertThat(Long.valueOf(stockDTO.getId())).isEqualTo(stocksBO.getId().intValue());
        assertThat(stockDTO.getSymbol()).isEqualTo(stocksBO.getSymbol());
        assertThat(stockDTO.getName()).isEqualTo(stocksBO.getName());
    }

    @Test
    public void testToDtoList() {

    	StocksBO stockBO1 = new StocksBO();
        stockBO1.setId(1L);
        stockBO1.setSymbol("AAPL");
        stockBO1.setName("Apple Inc");

        StocksBO stockBO2 = new StocksBO();
        stockBO2.setId(2L);
        stockBO2.setSymbol("GOOGL");
        stockBO2.setName("Google LLC");

        List<StocksBO> stockBOList = Arrays.asList(stockBO1, stockBO2);

        // Perform mapping
        List<Stocks> stockDTOs = stockMapper.toDtoList(stockBOList);

        // Assert mappings
        assertThat(stockDTOs).isNotNull();
        assertThat(stockDTOs).hasSize(2);
        assertThat(Long.valueOf(stockDTOs.get(0).getId())).isEqualTo(stockBO1.getId());
        assertThat(Long.valueOf(stockDTOs.get(1).getId())).isEqualTo(stockBO2.getId());
        assertThat(stockDTOs.get(0).getSymbol()).isEqualTo(stockBO1.getSymbol());
        assertThat(stockDTOs.get(1).getSymbol()).isEqualTo(stockBO2.getSymbol());
        assertThat(stockDTOs.get(0).getName()).isEqualTo(stockBO1.getName());
        assertThat(stockDTOs.get(1).getName()).isEqualTo(stockBO2.getName());
    }

    @Test
    public void testToBOList() {

    	Stocks stockDTO1 = new Stocks();
        stockDTO1.setId(1);
        stockDTO1.setSymbol("AAPL");
        stockDTO1.setName("Apple Inc");

        Stocks stockDTO2 = new Stocks();
        stockDTO2.setId(2);
        stockDTO2.setSymbol("GOOGL");
        stockDTO2.setName("Google LLC");

        List<Stocks> stockDTOList = Arrays.asList(stockDTO1, stockDTO2);

        // Perform mapping
        List<StocksBO> stockBOList = stockMapper.toBOList(stockDTOList);

        // Assert mappings
        assertThat(stockBOList).isNotNull();
        assertThat(stockBOList).hasSize(2);
        assertThat(stockBOList.get(0).getId()).isEqualTo(Long.valueOf(stockDTO1.getId()));
        assertThat(stockBOList.get(1).getId()).isEqualTo(Long.valueOf(stockDTO2.getId()));
        assertThat(stockBOList.get(0).getSymbol()).isEqualTo(stockDTO1.getSymbol());
        assertThat(stockBOList.get(1).getSymbol()).isEqualTo(stockDTO2.getSymbol());
        assertThat(stockBOList.get(0).getName()).isEqualTo(stockDTO1.getName());
        assertThat(stockBOList.get(1).getName()).isEqualTo(stockDTO2.getName());
    }

}
