package com.project.lms.repository;

import com.project.lms.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Grade entity
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    /**
     * Find all grades for a specific student
     * 
     * @param studentId Student's user ID
     * @return List of grades
     */
    List<Grade> findByStudent_Id(Long studentId);

    /**
     * Find all grades for a specific course
     * 
     * @param courseId Course ID
     * @return List of grades
     */
    List<Grade> findByCourse_Id(Long courseId);

    /**
     * Find grade for a specific student and course
     * 
     * @param studentId Student's user ID
     * @param courseId  Course ID
     * @return Optional containing the grade if found
     */
    Optional<Grade> findByStudent_IdAndCourse_Id(Long studentId, Long courseId);

    /**
     * Check if grade exists for student and course
     * 
     * @param studentId Student's user ID
     * @param courseId  Course ID
     * @return true if grade exists
     */
    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);
}
