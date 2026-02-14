package com.project.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Grade entity representing grades assigned to students for courses
 */
@Entity
@Table(name = "grades", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "student_id", "course_id" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double score; // Grade score (e.g., 0-100)

    @Column(columnDefinition = "TEXT")
    private String feedback; // Teacher's feedback

    @Column(nullable = false)
    private LocalDateTime gradedDate = LocalDateTime.now();

    // Many-to-One relationship with User (student)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User student;

    // Many-to-One relationship with Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Course course;

    @PrePersist
    protected void onCreate() {
        gradedDate = LocalDateTime.now();
    }
}
