package com.project.lms.repository;

import com.project.lms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by name
     * 
     * @param name Role name (e.g., ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT)
     * @return Optional containing the role if found
     */
    Optional<Role> findByName(String name);
}
