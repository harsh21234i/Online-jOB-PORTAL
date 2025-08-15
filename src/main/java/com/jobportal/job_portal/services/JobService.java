package com.jobportal.job_portal.services;

import com.jobportal.job_portal.models.Job;
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

    public Job postJob(Job job) {
        // Save the job to the database and return it
        return jobRepository.save(job);
    }
}
