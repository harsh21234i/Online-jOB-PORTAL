package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository provides basic CRUD operations, so you don't need to define findAll() explicitly.

    // Find user by email
    User findByEmail(String email);

    // Find user by ID
    Optional<User> findById(Long id);
}
