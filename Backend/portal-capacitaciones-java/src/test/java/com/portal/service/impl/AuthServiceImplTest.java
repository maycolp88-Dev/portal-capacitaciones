package com.portal.service.impl;

import com.portal.model.User;
import com.portal.repository.UserRepository;
import com.portal.service.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("john");
        testUser.setPassword("secret");
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreCorrect() {
        // Arrange
        when(userRepository.findByUsernameAndPassword("john", "secret"))
                .thenReturn(Optional.of(testUser));

        // Act
        Optional<User> result = authService.login("john", "secret");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
        verify(userRepository, times(1)).findByUsernameAndPassword("john", "secret");
    }

    @Test
    void login_ShouldReturnEmpty_WhenCredentialsAreInvalid() {
        // Arrange
        when(userRepository.findByUsernameAndPassword("john", "wrongpass"))
                .thenReturn(Optional.empty());

        // Act
        Optional<User> result = authService.login("john", "wrongpass");

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsernameAndPassword("john", "wrongpass");
    }

    @Test
    void login_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsernameAndPassword("nonexistent", "1234"))
                .thenReturn(Optional.empty());

        // Act
        Optional<User> result = authService.login("nonexistent", "1234");

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByUsernameAndPassword("nonexistent", "1234");
    }
}
