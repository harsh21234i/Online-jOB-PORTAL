package com.jobportal.job_portal.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing (enable for production)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll() // Public pages
                        .anyRequest().authenticated() // Any other pages require login
                )
                .formLogin(login -> login
                        .loginPage("/login") // Login page URL
                        .loginProcessingUrl("/perform_login") // URL for the login POST request
                        .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard after login
                        .failureUrl("/login?error=true") // Redirect to login page on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true") // Redirect to login after logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
