package com.jobportal.job_portal.services;

import com.jobportal.job_portal.models.User;
import com.jobportal.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Other methods...

    // 1. Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Assuming findByEmail method exists in your repository
    }
    public List<User> getAllUsers() {
        return userRepository.findAll(); // This calls the findAll() method of JpaRepository
    }
    // Method to get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id); // This uses the findById method of JpaRepository
    }

    // 2. Check password method
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword()); // Comparing raw password with encrypted one
    }
    public User saveUser(User user) {
        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Set the encoded password

        // Save the user to the database
        return userRepository.save(user); // This saves the user into the database and returns the saved user
    }


    // Other methods...
}
