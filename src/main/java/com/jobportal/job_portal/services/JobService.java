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

    // ✅ Search Jobs with filters
    public List<Job> searchJobs(String title, String company, String location, Double minSalary, Double maxSalary) {
        if ((title == null || title.isEmpty()) &&
                (company == null || company.isEmpty()) &&
                (location == null || location.isEmpty()) &&
                minSalary == null && maxSalary == null) {
            // No filters → return all
            return jobRepository.findAll();
        }
        return jobRepository.searchJobs(title, company, location, minSalary, maxSalary);
    }

    // ✅ Post new job
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    // ✅ Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // ✅ Get job by ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    // ✅ Delete job by ID
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
