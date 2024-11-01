package com.portfolio.management.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.StockList;
import com.portfolio.management.model.StockListBO;

@Mapper
public interface StockListMapper {
	
	StockListMapper INSTANCE = Mappers.getMapper(StockListMapper.class);
	
	List<StockList> toDtoList(List<StockListBO> stockBOList);

}
