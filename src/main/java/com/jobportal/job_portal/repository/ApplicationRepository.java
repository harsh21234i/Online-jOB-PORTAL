package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.Entity.Application;
import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find all applications by a specific job
    List<Application> findBySeeker(User seeker);

    // Find all applications for a specific job
    List<Application> findByJob(Job job);

    // Find all applications by status
    List<Application> findByStatus(Application.Status status);

    // Find applications for a seeker with a specific status
    List<Application> findBySeekerAndStatus(User seeker, Application.Status status);
}
