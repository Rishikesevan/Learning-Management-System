package com.project.lms.service;

import com.project.lms.dto.GradeDTO;
import com.project.lms.entity.Grade;

import java.util.List;

/**
 * Service interface for Grade operations
 */
public interface GradeService {

    /**
     * Assign or update a grade
     */
    Grade assignGrade(GradeDTO gradeDTO);

    /**
     * Get grades by student
     */
    List<Grade> getGradesByStudent(Long studentId);

    /**
     * Get grades by course
     */
    List<Grade> getGradesByCourse(Long courseId);

    /**
     * Get grade for student and course
     */
    Grade getGradeByStudentAndCourse(Long studentId, Long courseId);

    /**
     * Delete grade
     */
    void deleteGrade(Long id);
}
