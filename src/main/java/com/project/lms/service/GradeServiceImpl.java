package com.project.lms.service;

import com.project.lms.dto.GradeDTO;
import com.project.lms.entity.Course;
import com.project.lms.entity.Grade;
import com.project.lms.entity.User;
import com.project.lms.exception.ResourceNotFoundException;
import com.project.lms.repository.CourseRepository;
import com.project.lms.repository.GradeRepository;
import com.project.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of GradeService
 */
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Grade assignGrade(GradeDTO gradeDTO) {
        User student = userRepository.findById(gradeDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", gradeDTO.getStudentId()));

        Course course = courseRepository.findById(gradeDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", gradeDTO.getCourseId()));

        // Check if grade already exists, update if it does
        Grade grade = gradeRepository.findByStudent_IdAndCourse_Id(
                gradeDTO.getStudentId(), gradeDTO.getCourseId())
                .orElse(new Grade());

        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(gradeDTO.getScore());
        grade.setFeedback(gradeDTO.getFeedback());

        return gradeRepository.save(grade);
    }

    @Override
    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudent_Id(studentId);
    }

    @Override
    public List<Grade> getGradesByCourse(Long courseId) {
        return gradeRepository.findByCourse_Id(courseId);
    }

    @Override
    public Grade getGradeByStudentAndCourse(Long studentId, Long courseId) {
        return gradeRepository.findByStudent_IdAndCourse_Id(studentId, courseId)
                .orElse(null); // Return null if no grade exists yet
    }

    @Override
    @Transactional
    public void deleteGrade(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", id));
        gradeRepository.delete(grade);
    }
}
