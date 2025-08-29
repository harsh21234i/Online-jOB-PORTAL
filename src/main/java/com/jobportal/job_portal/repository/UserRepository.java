package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // ✅ Return Optional
}
