package com.jobportal.job_portal.services;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;



    public void postJob(Job job) {
        jobRepository.save(job);
    }
    public List<Job> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        System.out.println("Fetched Jobs: " + jobs.size());
        return jobs;
    }

}
