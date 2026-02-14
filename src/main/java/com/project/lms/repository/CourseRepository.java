package com.project.lms.repository;

import com.project.lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Course entity
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find all courses by teacher ID
     * 
     * @param teacherId Teacher's user ID
     * @return List of courses taught by the teacher
     */
    List<Course> findByTeacher_Id(Long teacherId);

    /**
     * Find all courses ordered by creation date (newest first)
     * 
     * @return List of all courses
     */
    List<Course> findAllByOrderByCreatedDateDesc();

    /**
     * Find courses by title containing keyword (case-insensitive)
     * 
     * @param keyword Search keyword
     * @return List of matching courses
     */
    List<Course> findByTitleContainingIgnoreCase(String keyword);
}
