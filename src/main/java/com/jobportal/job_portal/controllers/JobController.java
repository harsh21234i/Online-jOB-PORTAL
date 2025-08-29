package com.jobportal.job_portal.controllers;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.Entity.User;
import com.jobportal.job_portal.repository.JobRepository;
import com.jobportal.job_portal.services.JobService;
import com.jobportal.job_portal.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    // ✅ Job dashboard - list all jobs
    @GetMapping("/jobs")
    public String jobDashboard(HttpSession session, Model model) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        model.addAttribute("jobs", jobService.getAllJobs());
        return "dashboard"; // dashboard.html
    }

    // ✅ Search & filter jobs
    @GetMapping("/jobs/search")
    public String searchJobs(@RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String location,
                             @RequestParam(required = false) String jobType,
                             @RequestParam(required = false) Double minSalary,
                             @RequestParam(required = false) Double maxSalary,
                             HttpSession session,
                             Model model) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        model.addAttribute("jobs",
                jobService.searchJobs(keyword, location, jobType, minSalary, maxSalary));
        return "dashboard"; // reuse dashboard.html
    }

    // ✅ Show Post Job form
    @GetMapping("/jobs/post")
    public String postJobPage(HttpSession session) {
        if (session.getAttribute("username") == null) return "redirect:/login";
        return "post-job"; // post-job.html
    }

    // ✅ Handle Post Job form
    @PostMapping("/jobs/post")
    public String handlePostJob(@RequestParam String title,
                                @RequestParam String companyName,
                                @RequestParam String description,
                                @RequestParam(required = false) String location,
                                @RequestParam(required = false) String jobType,
                                @RequestParam(required = false) Double salary,
                                @RequestParam(required = false) String skills,
                                HttpSession session) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User user = userService.getUserByEmail(email);
        Job job = new Job(title, companyName, description);
        job.setLocation(location);
        job.setJobType(jobType);
        job.setSalary(salary);
        job.setSkills(skills);
        job.setPostedBy(user);

        jobService.postJob(job);
        return "redirect:/jobs"; // redirect to dashboard
    }

    // ✅ Show edit form (only owner can see it)
    @GetMapping("/job/edit/{id}")
    public String editJobForm(@PathVariable Long id, HttpSession session, Model model) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User currentUser = userService.getUserByEmail(email);
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));

        if (!job.getPostedBy().getId().equals(currentUser.getId())) {
            return "error/403"; // forbidden page
        }

        model.addAttribute("job", job);
        return "edit-job"; // edit-job.html
    }

    // ✅ Handle edit submission
    @PostMapping("/job/update/{id}")
    public String updateJob(@PathVariable Long id,
                            @ModelAttribute Job job,
                            HttpSession session) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User currentUser = userService.getUserByEmail(email);
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));

        if (!existingJob.getPostedBy().getId().equals(currentUser.getId())) {
            return "error/403"; // forbidden
        }

        existingJob.setTitle(job.getTitle());
        existingJob.setCompanyName(job.getCompanyName());
        existingJob.setDescription(job.getDescription());
        existingJob.setLocation(job.getLocation());
        existingJob.setJobType(job.getJobType());
        existingJob.setSalary(job.getSalary());
        existingJob.setSkills(job.getSkills());

        jobRepository.save(existingJob);
        return "redirect:/jobs"; // redirect to dashboard
    }

    // ✅ Delete job (only owner can delete)
    @GetMapping("/job/delete/{id}")
    public String deleteJob(@PathVariable Long id, HttpSession session) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User currentUser = userService.getUserByEmail(email);
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));

        if (!job.getPostedBy().getId().equals(currentUser.getId())) {
            return "error/403"; // forbidden
        }

        jobRepository.delete(job);
        return "redirect:/jobs"; // redirect to dashboard
    }
}
