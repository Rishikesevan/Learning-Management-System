package com.project.lms.repository;

import com.project.lms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Enrollment entity
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * Find all enrollments for a specific student
     * 
     * @param studentId Student's user ID
     * @return List of enrollments
     */
    List<Enrollment> findByStudent_Id(Long studentId);

    /**
     * Find all enrollments for a specific course
     * 
     * @param courseId Course ID
     * @return List of enrollments
     */
    List<Enrollment> findByCourse_Id(Long courseId);

    /**
     * Find enrollment for a specific student and course
     * 
     * @param studentId Student's user ID
     * @param courseId  Course ID
     * @return Optional containing the enrollment if found
     */
    Optional<Enrollment> findByStudent_IdAndCourse_Id(Long studentId, Long courseId);

    /**
     * Check if student is enrolled in a course
     * 
     * @param studentId Student's user ID
     * @param courseId  Course ID
     * @return true if enrolled
     */
    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);

    /**
     * Count enrollments for a course
     * 
     * @param courseId Course ID
     * @return Number of enrolled students
     */
    long countByCourse_Id(Long courseId);
}
