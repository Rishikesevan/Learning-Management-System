package com.project.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller for landing page and dashboard redirection
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    /**
     * Landing page
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Dashboard - redirects based on user role
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Redirect based on role
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
                return "redirect:/teacher/dashboard";
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
                return "redirect:/student/dashboard";
            }
        }
        return "redirect:/login";
    }
}
