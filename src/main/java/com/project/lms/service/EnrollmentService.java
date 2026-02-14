package com.project.lms.service;

import com.project.lms.entity.Enrollment;

import java.util.List;

/**
 * Service interface for Enrollment operations
 */
public interface EnrollmentService {

    /**
     * Enroll a student in a course
     */
    Enrollment enrollStudent(Long studentId, Long courseId);

    /**
     * Get enrollments by student
     */
    List<Enrollment> getEnrollmentsByStudent(Long studentId);

    /**
     * Get enrollments by course
     */
    List<Enrollment> getEnrollmentsByCourse(Long courseId);

    /**
     * Check if student is enrolled
     */
    boolean isStudentEnrolled(Long studentId, Long courseId);

    /**
     * Unenroll student from course
     */
    void unenrollStudent(Long studentId, Long courseId);
}
