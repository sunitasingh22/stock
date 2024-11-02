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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    private UserBO user;

    @BeforeEach
    void setUp() {
        user = new UserBO();
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        user.setPassword("password123");
        userRepository.save(user);
    }

    @Test
    void testFindByEmail() {
        Optional<UserBO> foundUser = userRepository.findByEmail("testuser@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testFindByUsername() {
        Optional<UserBO> foundUser = userRepository.findByUsername("testuser");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<UserBO> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void testFindByUsername_NotFound() {
        Optional<UserBO> foundUser = userRepository.findByUsername("nonexistentuser");
        assertThat(foundUser).isNotPresent();
    }
}
