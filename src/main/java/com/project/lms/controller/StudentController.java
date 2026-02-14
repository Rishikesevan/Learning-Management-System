package com.project.lms.controller;

import com.project.lms.entity.Course;
import com.project.lms.entity.Enrollment;
import com.project.lms.entity.Grade;
import com.project.lms.entity.User;
import com.project.lms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Student controller for viewing courses, lessons, and grades
 */
@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserService userService;
    private final CourseService courseService;
    private final LessonService lessonService;
    private final EnrollmentService enrollmentService;
    private final GradeService gradeService;

    /**
     * Student dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User student = userService.findByUsername(authentication.getName());
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student.getId());
        List<Grade> grades = gradeService.getGradesByStudent(student.getId());

        model.addAttribute("student", student);
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("totalEnrolledCourses", enrollments.size());
        model.addAttribute("recentGrades", grades.stream().limit(5).collect(Collectors.toList()));

        return "student/dashboard";
    }

    /**
     * List enrolled courses and available courses
     */
    @GetMapping("/courses")
    public String listCourses(Authentication authentication, Model model) {
        User student = userService.findByUsername(authentication.getName());
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student.getId());
        List<Course> allCourses = courseService.getAllCourses();

        // Get enrolled course IDs
        List<Long> enrolledCourseIds = enrollments.stream()
                .map(e -> e.getCourse().getId())
                .collect(Collectors.toList());

        // Filter available courses (not enrolled)
        List<Course> availableCourses = allCourses.stream()
                .filter(c -> !enrolledCourseIds.contains(c.getId()))
                .collect(Collectors.toList());

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("availableCourses", availableCourses);

        return "student/courses";
    }

    /**
     * Enroll in a course
     */
    @PostMapping("/enroll/{courseId}")
    public String enrollInCourse(@PathVariable Long courseId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            User student = userService.findByUsername(authentication.getName());
            enrollmentService.enrollStudent(student.getId(), courseId);
            redirectAttributes.addFlashAttribute("success", "Successfully enrolled in the course");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to enroll: " + e.getMessage());
        }

        return "redirect:/student/courses";
    }

    /**
     * Unenroll from a course
     */
    @PostMapping("/unenroll/{courseId}")
    public String unenrollFromCourse(@PathVariable Long courseId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            User student = userService.findByUsername(authentication.getName());
            enrollmentService.unenrollStudent(student.getId(), courseId);
            redirectAttributes.addFlashAttribute("success", "Successfully unenrolled from the course");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to unenroll: " + e.getMessage());
        }

        return "redirect:/student/courses";
    }

    /**
     * View course details
     */
    @GetMapping("/course/{id}")
    public String viewCourse(@PathVariable Long id,
            Authentication authentication,
            Model model) {
        User student = userService.findByUsername(authentication.getName());
        Course course = courseService.findById(id);

        // Check if student is enrolled
        boolean isEnrolled = enrollmentService.isStudentEnrolled(student.getId(), id);

        if (!isEnrolled) {
            return "redirect:/student/courses";
        }

        model.addAttribute("course", course);
        model.addAttribute("lessons", lessonService.getLessonsByCourse(id));

        // Get grade if exists
        Grade grade = gradeService.getGradeByStudentAndCourse(student.getId(), id);
        model.addAttribute("grade", grade);

        return "student/course-detail";
    }

    /**
     * View lessons for a course
     */
    @GetMapping("/lessons/{courseId}")
    public String viewLessons(@PathVariable Long courseId,
            Authentication authentication,
            Model model) {
        User student = userService.findByUsername(authentication.getName());

        // Check if student is enrolled
        if (!enrollmentService.isStudentEnrolled(student.getId(), courseId)) {
            return "redirect:/student/courses";
        }

        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("lessons", lessonService.getLessonsByCourse(courseId));

        return "student/lessons";
    }

    /**
     * View all grades
     */
    @GetMapping("/grades")
    public String viewGrades(Authentication authentication, Model model) {
        User student = userService.findByUsername(authentication.getName());
        List<Grade> grades = gradeService.getGradesByStudent(student.getId());

        model.addAttribute("grades", grades);

        // Calculate average grade
        if (!grades.isEmpty()) {
            double average = grades.stream()
                    .mapToDouble(Grade::getScore)
                    .average()
                    .orElse(0.0);
            model.addAttribute("averageGrade", String.format("%.2f", average));
        }

        return "student/grades";
    }
}
