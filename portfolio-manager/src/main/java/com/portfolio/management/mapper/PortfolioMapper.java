package com.portfolio.management.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.Portfolio;
import com.portfolio.management.model.PortfolioBO;

@Mapper
public interface PortfolioMapper {
	
	PortfolioMapper INSTANCE = Mappers.getMapper(PortfolioMapper.class);

	List<Portfolio> toDtoList(List<PortfolioBO> portfolioBOList);

	List<PortfolioBO> toBOList(List<Portfolio> portfolioList);
	
	Portfolio portfolioBOToDto(PortfolioBO portfolioBO);
	
	PortfolioBO portfolioDtoToBO(Portfolio portfolio);
	

}
