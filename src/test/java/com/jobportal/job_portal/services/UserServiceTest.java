package com.jobportal.job_portal.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.jobportal.job_portal.models.User;
import com.jobportal.job_portal.repository.UserRepository;
import com.jobportal.job_portal.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User("Alice", "alice@example.com", "password123", "EMPLOYER");
        User user2 = new User("Bob", "bob@example.com", "password123", "JOB_SEEKER");
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.getAllUsers(); // Ensure correct method call

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains(user1, user2);
    }

    @Test
    public void testGetUserById() {
        // Arrange
        User user = new User("John Doe", "john@example.com", "password123", "JOB_SEEKER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(1L); // Ensure correct method call

        // Assert
        assertThat(result).isPresent().contains(user);
    }
}
