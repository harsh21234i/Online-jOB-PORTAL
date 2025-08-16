package com.jobportal.job_portal.controllers;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.Entity.User;
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
    private UserService userService;

    // Job dashboard - list all jobs
    @GetMapping("/jobs")
    public String jobDashboard(HttpSession session, Model model) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        model.addAttribute("jobs", jobService.getAllJobs());
        return "dashboard"; // create job-dashboard.html
    }

    // Show Post Job form
    @GetMapping("/jobs/post")
    public String postJobPage(HttpSession session) {
        if (session.getAttribute("username") == null) return "redirect:/login";
        return "post-job"; // create post-job.html
    }

    // Handle Post Job form
    @PostMapping("/jobs/post")
    public String handlePostJob(@RequestParam String title,
                                @RequestParam String companyName,
                                @RequestParam String description,
                                HttpSession session) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User user = userService.getUserByEmail(email);
        Job job = new Job(title, companyName, description);
        jobService.postJob(job); // save job
        return "redirect:/jobs";
    }
}
