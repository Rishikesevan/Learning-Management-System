package com.project.lms.service;

import com.project.lms.dto.UserDTO;
import com.project.lms.entity.User;

import java.util.List;

/**
 * Service interface for User operations
 */
public interface UserService {

    /**
     * Register a new user
     */
    User registerUser(UserDTO userDTO, String roleName);

    /**
     * Find user by username
     */
    User findByUsername(String username);

    /**
     * Find user by ID
     */
    User findById(Long id);

    /**
     * Get all users
     */
    List<User> getAllUsers();

    /**
     * Get users by role
     */
    List<User> getUsersByRole(String roleName);

    /**
     * Update user
     */
    User updateUser(User user);

    /**
     * Delete user
     */
    void deleteUser(Long id);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
}
