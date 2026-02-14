package com.project.lms.service;

import com.project.lms.dto.CourseDTO;
import com.project.lms.entity.Course;
import com.project.lms.entity.User;
import com.project.lms.exception.ResourceNotFoundException;
import com.project.lms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of CourseService
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Course createCourse(CourseDTO courseDTO, User teacher) {
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setImage(courseDTO.getImage());
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacher_Id(teacherId);
    }

    @Override
    @Transactional
    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course course = findById(id);
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course course = findById(id);
        courseRepository.delete(course);
    }

    @Override
    public List<Course> searchCourses(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
