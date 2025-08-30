package com.jobportal.job_portal.services;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // --- Post new job ---
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    // --- Update existing job ---
    public Job updateJob(Job job) {
        if (jobRepository.existsById(job.getId())) {
            return jobRepository.save(job); // Save updates
        }
        return null;
    }

    // --- Get job by ID ---
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    // --- Get all jobs ---
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // --- Delete job ---
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // --- Search jobs ---
    public List<Job> searchJobs(String title, String company, String location, Double minSalary, Double maxSalary) {
        if ((title == null || title.isEmpty()) &&
                (company == null || company.isEmpty()) &&
                (location == null || location.isEmpty()) &&
                minSalary == null && maxSalary == null) {
            return jobRepository.findAll();
        }
        return jobRepository.findAll().stream()
                .filter(j -> title == null || j.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(j -> company == null || j.getCompanyName().toLowerCase().contains(company.toLowerCase()))
                .filter(j -> location == null || j.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(j -> minSalary == null || j.getSalary() >= minSalary)
                .filter(j -> maxSalary == null || j.getSalary() <= maxSalary)
                .toList();
    }

    // --- Search by keyword ---
    public List<Job> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return jobRepository.findAll();
        }
        return jobRepository.findByKeyword(keyword, PageRequest.of(0, 100)).getContent();
    }

    // --- Get jobs with pagination ---
    public Page<Job> getJobsWithPagination(String keyword,
                                           String title, String company, String location,
                                           Double minSalary, Double maxSalary,
                                           int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword != null && !keyword.isEmpty()) {
            return jobRepository.findByKeyword(keyword, pageable);
        }
        title = (title != null && !title.isEmpty()) ? title : null;
        company = (company != null && !company.isEmpty()) ? company : null;
        location = (location != null && !location.isEmpty()) ? location : null;
        return jobRepository.searchJobs(title, company, location, minSalary, maxSalary, pageable);
    }
}
