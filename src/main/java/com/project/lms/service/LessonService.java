package com.project.lms.service;

import com.project.lms.dto.LessonDTO;
import com.project.lms.entity.Lesson;

import java.util.List;

/**
 * Service interface for Lesson operations
 */
public interface LessonService {

    /**
     * Create a new lesson
     */
    Lesson createLesson(LessonDTO lessonDTO);

    /**
     * Find lesson by ID
     */
    Lesson findById(Long id);

    /**
     * Get lessons by course
     */
    List<Lesson> getLessonsByCourse(Long courseId);

    /**
     * Update lesson
     */
    Lesson updateLesson(Long id, LessonDTO lessonDTO);

    /**
     * Delete lesson
     */
    void deleteLesson(Long id);
}
