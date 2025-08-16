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
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    // Show job dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("jobs", jobService.getAllJobs());
        return "job-dashboard";
    }

    // Show job posting form
    @GetMapping("/post")
    public String showPostForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        model.addAttribute("job", new Job());
        return "post-job";
    }

    // Handle job posting
    @PostMapping("/post")
    public String handlePost(@ModelAttribute Job job, HttpSession session) {
        String email = (String) session.getAttribute("username");
        if (email == null) return "redirect:/login";

        User user = userService.getUserByEmail(email);
        job.setPostedBy(user);
        jobService.saveJob(job);

        return "redirect:/jobs/dashboard";
    }
}
