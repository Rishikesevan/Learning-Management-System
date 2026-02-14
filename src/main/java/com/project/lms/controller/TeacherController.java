package com.project.lms.controller;

import com.project.lms.dto.CourseDTO;
import com.project.lms.dto.GradeDTO;
import com.project.lms.dto.LessonDTO;
import com.project.lms.entity.Course;
import com.project.lms.entity.Enrollment;
import com.project.lms.entity.User;
import com.project.lms.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Teacher controller for course and lesson management
 */
@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final UserService userService;
    private final CourseService courseService;
    private final LessonService lessonService;
    private final EnrollmentService enrollmentService;
    private final GradeService gradeService;

    /**
     * Teacher dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User teacher = userService.findByUsername(authentication.getName());
        List<Course> courses = courseService.getCoursesByTeacher(teacher.getId());

        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courses);
        model.addAttribute("totalCourses", courses.size());

        return "teacher/dashboard";
    }

    /**
     * List teacher's courses
     */
    @GetMapping("/courses")
    public String listCourses(Authentication authentication, Model model) {
        User teacher = userService.findByUsername(authentication.getName());
        model.addAttribute("courses", courseService.getCoursesByTeacher(teacher.getId()));
        return "teacher/courses";
    }

    /**
     * Show create course form
     */
    @GetMapping("/create-course")
    public String showCreateCourseForm(Model model) {
        model.addAttribute("courseDTO", new CourseDTO());
        return "teacher/create-course";
    }

    /**
     * Create new course
     */
    @PostMapping("/create-course")
    public String createCourse(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
            BindingResult result,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "teacher/create-course";
        }

        try {
            // Handle image upload
            if (courseDTO.getImageFile() != null && !courseDTO.getImageFile().isEmpty()) {
                String uploadDir = "src/main/resources/static/image/";
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get(uploadDir));

                String fileName = java.util.UUID.randomUUID().toString() + "_"
                        + courseDTO.getImageFile().getOriginalFilename();
                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir + fileName);
                java.nio.file.Files.copy(courseDTO.getImageFile().getInputStream(), filePath,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                courseDTO.setImage("/image/" + fileName);
            }

            User teacher = userService.findByUsername(authentication.getName());
            courseService.createCourse(courseDTO, teacher);
            redirectAttributes.addFlashAttribute("success", "Course created successfully");
            return "redirect:/teacher/courses";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to create course: " + e.getMessage());
            return "redirect:/teacher/create-course";
        }
    }

    /**
     * View course details
     */
    @GetMapping("/course/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("lessons", lessonService.getLessonsByCourse(id));
        model.addAttribute("enrollments", enrollmentService.getEnrollmentsByCourse(id));
        return "teacher/course-detail";
    }

    /**
     * Delete course
     */
    @PostMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete course: " + e.getMessage());
        }
        return "redirect:/teacher/courses";
    }

    /**
     * Show upload lesson form
     */
    @GetMapping("/upload-lesson")
    public String showUploadLessonForm(@RequestParam(required = false) Long courseId,
            Authentication authentication,
            Model model) {
        User teacher = userService.findByUsername(authentication.getName());
        model.addAttribute("lessonDTO", new LessonDTO());
        model.addAttribute("courses", courseService.getCoursesByTeacher(teacher.getId()));

        if (courseId != null) {
            model.addAttribute("selectedCourseId", courseId);
        }

        return "teacher/upload-lesson";
    }

    /**
     * Upload new lesson
     */
    @PostMapping("/upload-lesson")
    public String uploadLesson(@Valid @ModelAttribute("lessonDTO") LessonDTO lessonDTO,
            BindingResult result,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            User teacher = userService.findByUsername(authentication.getName());
            model.addAttribute("courses", courseService.getCoursesByTeacher(teacher.getId()));
            return "teacher/upload-lesson";
        }

        try {
            lessonService.createLesson(lessonDTO);
            redirectAttributes.addFlashAttribute("success", "Lesson uploaded successfully");
            return "redirect:/teacher/course/" + lessonDTO.getCourseId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload lesson: " + e.getMessage());
            return "redirect:/teacher/upload-lesson";
        }
    }

    /**
     * Delete lesson
     */
    @PostMapping("/delete-lesson/{id}")
    public String deleteLesson(@PathVariable Long id,
            @RequestParam Long courseId,
            RedirectAttributes redirectAttributes) {
        try {
            lessonService.deleteLesson(id);
            redirectAttributes.addFlashAttribute("success", "Lesson deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete lesson: " + e.getMessage());
        }
        return "redirect:/teacher/course/" + courseId;
    }

    /**
     * Show grade students page
     */
    @GetMapping("/grade-students")
    public String showGradeStudentsPage(@RequestParam(required = false) Long courseId,
            Authentication authentication,
            Model model) {
        User teacher = userService.findByUsername(authentication.getName());
        List<Course> courses = courseService.getCoursesByTeacher(teacher.getId());

        model.addAttribute("courses", courses);

        if (courseId != null) {
            Course course = courseService.findById(courseId);
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);

            model.addAttribute("selectedCourse", course);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("gradeDTO", new GradeDTO());
        }

        return "teacher/grade-students";
    }

    /**
     * Assign grade to student
     */
    @PostMapping("/assign-grade")
    public String assignGrade(@Valid @ModelAttribute("gradeDTO") GradeDTO gradeDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Invalid grade data");
            return "redirect:/teacher/grade-students?courseId=" + gradeDTO.getCourseId();
        }

        try {
            gradeService.assignGrade(gradeDTO);
            redirectAttributes.addFlashAttribute("success", "Grade assigned successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to assign grade: " + e.getMessage());
        }

        return "redirect:/teacher/grade-students?courseId=" + gradeDTO.getCourseId();
    }
}
