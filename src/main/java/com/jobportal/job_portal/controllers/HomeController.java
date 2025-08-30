package com.jobportal.job_portal.controllers;

import com.jobportal.job_portal.Entity.Job;
import com.jobportal.job_portal.Entity.User;
import com.jobportal.job_portal.services.JobService;
import com.jobportal.job_portal.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService userService;
    private final JobService jobService;

    public HomeController(UserService userService, JobService jobService) {
        this.userService = userService;
        this.jobService = jobService;
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam(required = false) String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role != null ? role : "USER");
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String company,
                            @RequestParam(required = false) String location,
                            @RequestParam(required = false) Double minSalary,
                            @RequestParam(required = false) Double maxSalary,
                            @RequestParam(defaultValue = "0") int page,
                            Model model) {

        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);

        int pageSize = 5; // Jobs per page

        Page<Job> jobPage = jobService.getJobsWithPagination(keyword, title, company, location, minSalary, maxSalary, page, pageSize);
        model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobPage.getTotalPages());

        // Preserve filter values in the form
        model.addAttribute("filterTitle", title);
        model.addAttribute("filterCompany", company);
        model.addAttribute("filterLocation", location);
        model.addAttribute("filterMinSalary", minSalary);
        model.addAttribute("filterMaxSalary", maxSalary);
        model.addAttribute("keyword", keyword);

        return "dashboard";
    }


    // ✅ Show edit job form
    @GetMapping("/jobs/edit/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        if (job == null) {
            return "redirect:/dashboard"; // job not found
        }
        model.addAttribute("job", job);
        return "edit_job"; // create edit_job.html
    }

    // ✅ Handle edit job submission
    @PostMapping("/jobs/edit")
    public String editJobSubmit(@RequestParam Long id,
                                @RequestParam String title,
                                @RequestParam String companyName,
                                @RequestParam String location,
                                @RequestParam Double salary,
                                @RequestParam String skills) {

        Job job = jobService.getJobById(id);
        if (job != null) {
            job.setTitle(title);
            job.setCompanyName(companyName);
            job.setLocation(location);
            job.setSalary(salary);
            job.setSkills(skills);
            jobService.postJob(job); // save updated job
        }
        return "redirect:/dashboard";
    }

    // ✅ Delete job
    @GetMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/dashboard";
    }

}
