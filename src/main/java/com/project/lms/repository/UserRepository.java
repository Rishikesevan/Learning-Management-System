package com.project.lms.repository;

import com.project.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     * 
     * @param username Username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * 
     * @param email Email address
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all users with a specific role
     * 
     * @param roleName Role name (e.g., ROLE_STUDENT, ROLE_TEACHER)
     * @return List of users with the specified role
     */
    List<User> findByRoles_Name(String roleName);

    /**
     * Check if username exists
     * 
     * @param username Username to check
     * @return true if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     * 
     * @param email Email to check
     * @return true if email exists
     */
    boolean existsByEmail(String email);
}
