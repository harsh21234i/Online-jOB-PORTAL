package com.jobportal.job_portal.controllers;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.services.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Show Post Job Form
    @GetMapping("/post")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "post-job";
    }

    // Handle Form Submission
    @PostMapping("/post")
    public String postJob(@ModelAttribute Job job) {
        jobService.postJob(job);
        return "redirect:/dashboard";
    }
}
