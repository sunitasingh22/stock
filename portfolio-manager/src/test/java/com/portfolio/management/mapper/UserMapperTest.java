package com.portfolio.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.portfolio.management.dto.Users;
import com.portfolio.management.model.UserBO;

public class UserMapperTest {
	private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testUserBOToDto() {
        UserBO userBO = new UserBO();
        userBO.setId(1L);
        userBO.setUsername("sai");
        userBO.setEmail("sai@gmail.com");

        Users userDTO = userMapper.userBOToDto(userBO);

        // Assert mappings
        assertThat(userDTO).isNotNull();
        assertThat(Long.valueOf(userDTO.getId())).isEqualTo(userBO.getId());
        assertThat(userDTO.getUsername()).isEqualTo(userBO.getUsername());
        assertThat(userDTO.getEmail()).isEqualTo(userBO.getEmail());
    }

    @Test
    public void testUserDtoToBO() {
        Users userDTO = new Users();
        userDTO.setId(1);
        userDTO.setUsername("sai");
        userDTO.setEmail("sai@gmail.com");

        UserBO userBO = userMapper.userDtoToBO(userDTO);

        // Assert mappings
        assertThat(userBO).isNotNull();
        assertThat(userBO.getId()).isEqualTo(Long.valueOf(userDTO.getId()));
        assertThat(userBO.getUsername()).isEqualTo(userDTO.getUsername());
        assertThat(userBO.getEmail()).isEqualTo(userDTO.getEmail());
    }

}
