package com.jobportal.job_portal.services;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Save a job
    public Job postJob(Job job) {
        return jobRepository.save(job); // ✅ use instance, not class name
    }
}
