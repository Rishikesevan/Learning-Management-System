package com.project.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Lesson creation and updates
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    @NotBlank(message = "Lesson title is required")
    @Size(min = 3, max = 200, message = "Lesson title must be between 3 and 200 characters")
    private String title;

    @NotBlank(message = "Lesson content is required")
    @Size(min = 10, message = "Lesson content must be at least 10 characters")
    private String content;

    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    @NotNull(message = "Course ID is required")
    private Long courseId;
}
