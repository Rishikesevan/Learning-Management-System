package com.project.lms.controller;

import com.project.lms.dto.UserDTO;
import com.project.lms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Authentication controller for login and registration
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Show login page
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    /**
     * Show registration page
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    /**
     * Process registration form
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "register";
        }

        // Check if username already exists
        if (userService.existsByUsername(userDTO.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // Check if email already exists
        if (userService.existsByEmail(userDTO.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        try {
            // Determine role (default to STUDENT if not provided or invalid)
            String role = userDTO.getRole();
            if (role == null || (!role.equals("ROLE_TEACHER") && !role.equals("ROLE_ADMIN"))) {
                role = "ROLE_STUDENT";
            }

            // Register user
            userService.registerUser(userDTO, role);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}
