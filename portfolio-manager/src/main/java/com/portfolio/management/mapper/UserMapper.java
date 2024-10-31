package com.portfolio.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.Users;
import com.portfolio.management.model.UserBO;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	Users userBOToDto(UserBO userBO);
	
	UserBO userDtoToBO(Users user);

}
