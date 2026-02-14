package com.project.lms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Grade assignment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {

    @NotNull(message = "Score is required")
    @DecimalMin(value = "0.0", message = "Score must be at least 0")
    @DecimalMax(value = "100.0", message = "Score must not exceed 100")
    private Double score;

    @Size(max = 1000, message = "Feedback must not exceed 1000 characters")
    private String feedback;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;
}
