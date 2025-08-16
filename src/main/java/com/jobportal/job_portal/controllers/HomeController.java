package com.jobportal.job_portal.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.jobportal.job_portal.Entity.User;
import com.jobportal.job_portal.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    // Home page / dashboard
    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username != null ? username : "Guest");
        return "index";
    }

    // Login page
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/"; // already logged in
        }
        return "login";
    }

    // Handle login POST
    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        User user = userService.getUserByEmail(email);
        if (user != null && userService.checkPassword(user, password)) {
            session.setAttribute("username", user.getEmail()); // email as username
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login";
        }
    }

    // Registration page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Handle registration POST
    @PostMapping("/register")
    public String handleRegistration(@RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam(required = false) String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);          // email as username
        user.setPassword(password);    // will encode in service
        user.setRole(role != null ? role : "USER"); // default role if null
        userService.saveUser(user);
        return "redirect:/login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String email = (String) session.getAttribute("username");
        if(email == null) return "redirect:/login";
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "dashboard"; // create dashboard.html
    }

}
