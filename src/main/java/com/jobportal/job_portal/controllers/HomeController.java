package com.jobportal.job_portal.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.jobportal.job_portal.models.User;
import com.jobportal.job_portal.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService; // Inject the actual UserService class

    // 1. Display Home Page
    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        // Check if user is in session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            model.addAttribute("username", "Guest");
        } else {
            model.addAttribute("username", username);
        }
        return "index";  // Renders index.html
    }

    // 2. Show Login Page
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // Check if user is already logged in
        if (session.getAttribute("username") != null) {
            return "redirect:/";  // Redirect to home if already logged in
        }
        return "login";  // Renders login.html
    }

    // 3. Handle Login (Manual)
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        // Check if user exists in the database and passwords match
        User user = userService.getUserByEmail(email);
        if (user != null && userService.checkPassword(user, password)) {
            // Store user in session
            session.setAttribute("username", email);
            return "redirect:/dashboard";  // Redirect to dashboard after login
        } else {
            // Show error on login page
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login";
        }
    }

    // 4. Show Register Page
    @GetMapping("/register")
    public String registerPage() {
        return "register";  // Renders register.html
    }

    // 5. Handle Registration
    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ) {
        // Encrypt password and save the user to the database
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);  // Password will be encrypted in the service layer

        userService.saveUser(user); // Save user to DB
        return "redirect:/login";  // Redirect to login page
    }

    // 6. Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Clear the session
        return "redirect:/";    // Redirect to home page
    }
}
