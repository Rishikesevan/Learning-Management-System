package com.project.lms.service;

import com.project.lms.dto.CourseDTO;
import com.project.lms.entity.Course;
import com.project.lms.entity.User;

import java.util.List;

/**
 * Service interface for Course operations
 */
public interface CourseService {

    /**
     * Create a new course
     */
    Course createCourse(CourseDTO courseDTO, User teacher);

    /**
     * Find course by ID
     */
    Course findById(Long id);

    /**
     * Get all courses
     */
    List<Course> getAllCourses();

    /**
     * Get courses by teacher
     */
    List<Course> getCoursesByTeacher(Long teacherId);

    /**
     * Update course
     */
    Course updateCourse(Long id, CourseDTO courseDTO);

    /**
     * Delete course
     */
    void deleteCourse(Long id);

    /**
     * Search courses by title
     */
    List<Course> searchCourses(String keyword);
}
