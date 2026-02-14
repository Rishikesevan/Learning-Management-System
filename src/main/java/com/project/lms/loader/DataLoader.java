package com.project.lms.loader;

import com.project.lms.entity.*;
import com.project.lms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Data loader to initialize the database with sample data
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

        private final RoleRepository roleRepository;
        private final UserRepository userRepository;
        private final CourseRepository courseRepository;
        private final LessonRepository lessonRepository;
        private final EnrollmentRepository enrollmentRepository;
        private final GradeRepository gradeRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
                // Check if data already exists
                if (roleRepository.count() > 0) {
                        log.info("Database already initialized. Skipping data loading.");
                        return;
                }

                log.info("Initializing database with sample data...");

                // Create Roles
                Role adminRole = new Role("ROLE_ADMIN");
                Role teacherRole = new Role("ROLE_TEACHER");
                Role studentRole = new Role("ROLE_STUDENT");

                roleRepository.save(adminRole);
                roleRepository.save(teacherRole);
                roleRepository.save(studentRole);
                log.info("Roles created successfully");

                // Create Admin User
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@lms.com");
                admin.setFullName("System Administrator");
                admin.setEnabled(true);
                admin.setRoles(new HashSet<>());
                admin.addRole(adminRole);
                userRepository.save(admin);
                log.info("Admin user created: admin/admin123");

                // Create Teacher User
                User teacher = new User();
                teacher.setUsername("teacher");
                teacher.setPassword(passwordEncoder.encode("teacher123"));
                teacher.setEmail("teacher@lms.com");
                teacher.setFullName("John Teacher");
                teacher.setEnabled(true);
                teacher.setRoles(new HashSet<>());
                teacher.addRole(teacherRole);
                userRepository.save(teacher);
                log.info("Teacher user created: teacher/teacher123");

                // Create Student User
                User student = new User();
                student.setUsername("student");
                student.setPassword(passwordEncoder.encode("student123"));
                student.setEmail("student@lms.com");
                student.setFullName("Jane Student");
                student.setEnabled(true);
                student.setRoles(new HashSet<>());
                student.addRole(studentRole);
                userRepository.save(student);
                log.info("Student user created: student/student123");

                // Create additional students for testing
                User student2 = new User();
                student2.setUsername("alice");
                student2.setPassword(passwordEncoder.encode("alice123"));
                student2.setEmail("alice@lms.com");
                student2.setFullName("Alice Johnson");
                student2.setEnabled(true);
                student2.setRoles(new HashSet<>());
                student2.addRole(studentRole);
                userRepository.save(student2);

                User student3 = new User();
                student3.setUsername("bob");
                student3.setPassword(passwordEncoder.encode("bob123"));
                student3.setEmail("bob@lms.com");
                student3.setFullName("Bob Williams");
                student3.setEnabled(true);
                student3.setRoles(new HashSet<>());
                student3.addRole(studentRole);
                userRepository.save(student3);
                log.info("Additional students created");

                // Create Sample Courses
                Course course1 = new Course();
                course1.setTitle("Introduction to Java Programming");
                course1.setDescription(
                                "Learn the fundamentals of Java programming language, including OOP concepts, data structures, and best practices.");
                course1.setTeacher(teacher);
                courseRepository.save(course1);

                Course course2 = new Course();
                course2.setTitle("Web Development with Spring Boot");
                course2.setDescription(
                                "Master Spring Boot framework to build modern web applications with REST APIs, security, and database integration.");
                course2.setTeacher(teacher);
                courseRepository.save(course2);

                Course course3 = new Course();
                course3.setTitle("Database Design and SQL");
                course3.setDescription(
                                "Comprehensive guide to relational database design, SQL queries, normalization, and performance optimization.");
                course3.setTeacher(teacher);
                courseRepository.save(course3);
                log.info("Sample courses created");

                // Create Sample Lessons for Course 1
                Lesson lesson1_1 = new Lesson();
                lesson1_1.setTitle("Introduction to Java");
                lesson1_1.setContent(
                                "Java is a high-level, class-based, object-oriented programming language. In this lesson, we'll cover the basics of Java syntax, variables, and data types.");
                lesson1_1.setOrderNumber(1);
                lesson1_1.setCourse(course1);
                lessonRepository.save(lesson1_1);

                Lesson lesson1_2 = new Lesson();
                lesson1_2.setTitle("Object-Oriented Programming Concepts");
                lesson1_2.setContent(
                                "Learn about the four pillars of OOP: Encapsulation, Inheritance, Polymorphism, and Abstraction. We'll explore each concept with practical examples.");
                lesson1_2.setOrderNumber(2);
                lesson1_2.setCourse(course1);
                lessonRepository.save(lesson1_2);

                Lesson lesson1_3 = new Lesson();
                lesson1_3.setTitle("Java Collections Framework");
                lesson1_3.setContent(
                                "Explore the Java Collections Framework including List, Set, Map, and Queue interfaces. Learn when to use ArrayList, HashMap, and other implementations.");
                lesson1_3.setOrderNumber(3);
                lesson1_3.setCourse(course1);
                lessonRepository.save(lesson1_3);

                // Create Sample Lessons for Course 2
                Lesson lesson2_1 = new Lesson();
                lesson2_1.setTitle("Getting Started with Spring Boot");
                lesson2_1.setContent(
                                "Spring Boot makes it easy to create stand-alone, production-grade Spring applications. Learn how to set up your first Spring Boot project.");
                lesson2_1.setOrderNumber(1);
                lesson2_1.setCourse(course2);
                lessonRepository.save(lesson2_1);

                Lesson lesson2_2 = new Lesson();
                lesson2_2.setTitle("Building REST APIs");
                lesson2_2.setContent(
                                "Create RESTful web services using Spring Boot. Learn about @RestController, @RequestMapping, and handling HTTP requests.");
                lesson2_2.setOrderNumber(2);
                lesson2_2.setCourse(course2);
                lessonRepository.save(lesson2_2);

                // Create Sample Lessons for Course 3
                Lesson lesson3_1 = new Lesson();
                lesson3_1.setTitle("Introduction to Databases");
                lesson3_1.setContent(
                                "Understand what databases are, different types of databases, and when to use relational vs non-relational databases.");
                lesson3_1.setOrderNumber(1);
                lesson3_1.setCourse(course3);
                lessonRepository.save(lesson3_1);

                Lesson lesson3_2 = new Lesson();
                lesson3_2.setTitle("SQL Basics");
                lesson3_2.setContent(
                                "Learn fundamental SQL commands: SELECT, INSERT, UPDATE, DELETE. Practice writing queries to retrieve and manipulate data.");
                lesson3_2.setOrderNumber(2);
                lesson3_2.setCourse(course3);
                lessonRepository.save(lesson3_2);
                log.info("Sample lessons created");

                // Create Sample Enrollments
                Enrollment enrollment1 = new Enrollment();
                enrollment1.setStudent(student);
                enrollment1.setCourse(course1);
                enrollmentRepository.save(enrollment1);

                Enrollment enrollment2 = new Enrollment();
                enrollment2.setStudent(student);
                enrollment2.setCourse(course2);
                enrollmentRepository.save(enrollment2);

                Enrollment enrollment3 = new Enrollment();
                enrollment3.setStudent(student2);
                enrollment3.setCourse(course1);
                enrollmentRepository.save(enrollment3);

                Enrollment enrollment4 = new Enrollment();
                enrollment4.setStudent(student3);
                enrollment4.setCourse(course1);
                enrollmentRepository.save(enrollment4);

                Enrollment enrollment5 = new Enrollment();
                enrollment5.setStudent(student3);
                enrollment5.setCourse(course3);
                enrollmentRepository.save(enrollment5);
                log.info("Sample enrollments created");

                // Create Sample Grades
                Grade grade1 = new Grade();
                grade1.setStudent(student);
                grade1.setCourse(course1);
                grade1.setScore(85.5);
                grade1.setFeedback(
                                "Excellent work! You have a strong grasp of Java fundamentals. Keep practicing OOP concepts.");
                gradeRepository.save(grade1);

                Grade grade2 = new Grade();
                grade2.setStudent(student2);
                grade2.setCourse(course1);
                grade2.setScore(92.0);
                grade2.setFeedback(
                                "Outstanding performance! Your code quality and understanding of concepts are impressive.");
                gradeRepository.save(grade2);

                Grade grade3 = new Grade();
                grade3.setStudent(student3);
                grade3.setCourse(course1);
                grade3.setScore(78.0);
                grade3.setFeedback("Good effort! Focus more on exception handling and best practices for improvement.");
                gradeRepository.save(grade3);
                log.info("Sample grades created");

                log.info("Database initialization completed successfully!");
                log.info("===========================================");
                log.info("Sample Login Credentials:");
                log.info("Admin    - username: admin    password: admin123");
                log.info("Teacher  - username: teacher  password: teacher123");
                log.info("Student  - username: student  password: student123");
                log.info("===========================================");
        }
}
