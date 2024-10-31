package com.portfolio.management.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.Stocks;
import com.portfolio.management.model.StocksBO;

@Mapper
public interface StockMapper {
	
	StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);
	
	StocksBO stockDtoToBO(Stocks stock);
	
	Stocks stockBOToDto(StocksBO stocksBO);
	
	List<Stocks> toDtoList(List<StocksBO> stockBOList);
	
	List<StocksBO> toBOList(List<Stocks> stockList);

}
