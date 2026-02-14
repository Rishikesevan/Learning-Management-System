package com.project.lms.controller;

import com.project.lms.dto.UserDTO;
import com.project.lms.service.CourseService;
import com.project.lms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Admin controller for administrative functions
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;

    /**
     * Admin dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalUsers = userService.getAllUsers().size();
        long totalCourses = courseService.getAllCourses().size();
        long totalStudents = userService.getUsersByRole("ROLE_STUDENT").size();
        long totalTeachers = userService.getUsersByRole("ROLE_TEACHER").size();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalTeachers", totalTeachers);

        return "admin/dashboard";
    }

    /**
     * List all users
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    /**
     * Show create user form
     */
    @GetMapping("/create-user")
    public String showCreateUserForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "admin/create-user";
    }

    /**
     * Create new user
     */
    @PostMapping("/create-user")
    public String createUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            return "admin/create-user";
        }

        // Check if username already exists
        if (userService.existsByUsername(userDTO.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "admin/create-user";
        }

        // Check if email already exists
        if (userService.existsByEmail(userDTO.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "admin/create-user";
        }

        try {
            // Determine role from form
            String roleName = userDTO.getRole();
            if (roleName == null || roleName.isEmpty()) {
                roleName = "ROLE_STUDENT"; // Default role
            }

            userService.registerUser(userDTO, roleName);
            redirectAttributes.addFlashAttribute("success", "User created successfully");
            return "redirect:/admin/users";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create user: " + e.getMessage());
            return "admin/create-user";
        }
    }

    /**
     * Delete user
     */
    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * List all courses
     */
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/courses";
    }
}
