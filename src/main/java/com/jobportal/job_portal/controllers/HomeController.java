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
    private UserService userService; // Injected service instance

    // 1. Display Home Page
    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username != null ? username : "Guest");
        return "index";
    }

    // 2. Show Login Page
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }
        return "login";
    }

    // 3. Handle Login
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        // ✅ Use injected instance, not the class
        User user = userService.getUserByEmail(email);
        if (user != null && userService.checkPassword(user, password)) {
            session.setAttribute("username", email);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login";
        }
    }

    // 4. Show Register Page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 5. Handle Registration
    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // will be encoded in service

        userService.saveUser(user); // ✅ use instance
        return "redirect:/login";
    }

    // 6. Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
