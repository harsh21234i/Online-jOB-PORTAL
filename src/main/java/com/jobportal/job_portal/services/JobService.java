package com.jobportal.job_portal.services;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Save a job (used in controller)
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }


    // List all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
