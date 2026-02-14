package com.project.lms.service;

import com.project.lms.dto.LessonDTO;
import com.project.lms.entity.Course;
import com.project.lms.entity.Lesson;
import com.project.lms.exception.ResourceNotFoundException;
import com.project.lms.repository.CourseRepository;
import com.project.lms.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of LessonService
 */
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Lesson createLesson(LessonDTO lessonDTO) {
        Course course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", lessonDTO.getCourseId()));

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setOrderNumber(lessonDTO.getOrderNumber());
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id));
    }

    @Override
    public List<Lesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourse_IdOrderByOrderNumber(courseId);
    }

    @Override
    @Transactional
    public Lesson updateLesson(Long id, LessonDTO lessonDTO) {
        Lesson lesson = findById(id);
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setOrderNumber(lessonDTO.getOrderNumber());
        return lessonRepository.save(lesson);
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        Lesson lesson = findById(id);
        lessonRepository.delete(lesson);
    }
}
