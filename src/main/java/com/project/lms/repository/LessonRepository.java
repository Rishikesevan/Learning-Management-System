package com.project.lms.repository;

import com.project.lms.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Lesson entity
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Find all lessons for a specific course, ordered by order number
     * 
     * @param courseId Course ID
     * @return List of lessons in order
     */
    List<Lesson> findByCourse_IdOrderByOrderNumber(Long courseId);

    /**
     * Count lessons in a course
     * 
     * @param courseId Course ID
     * @return Number of lessons
     */
    long countByCourse_Id(Long courseId);
}
