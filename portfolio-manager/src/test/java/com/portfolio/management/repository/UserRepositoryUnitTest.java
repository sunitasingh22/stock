package com.portfolio.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.management.model.UserBO;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryUnitTest {
	
	@Autowired
    private UserRepository userRepository;

    private UserBO user;

    @BeforeEach
    public void setUp() {
        user = new UserBO();
        user.setId(1L); 
        user.setEmail("sai@gmail.com");
        user.setUsername("sai");
        user.setPassword("123123");
        userRepository.save(user);
    }

    @Test
    public void testFindByEmail() {
        Optional<UserBO> userFromDB = userRepository.findByEmail(user.getEmail());

        assertThat(userFromDB).isPresent();
        assertThat(userFromDB.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindByUsername() {
        Optional<UserBO> userFromDB = userRepository.findByUsername(user.getUsername());

        assertThat(userFromDB).isPresent();
        assertThat(userFromDB.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void testFindByEmailNotFound() {
        Optional<UserBO> userFromDB = userRepository.findByEmail("abcd@gmail.com");

        assertThat(userFromDB).isNotPresent();
    }

    @Test
    public void testFindByUsernameNotFound() {
        Optional<UserBO> foundUser = userRepository.findByUsername("stranger");

        assertThat(foundUser).isNotPresent();
    }

}
