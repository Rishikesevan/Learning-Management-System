package com.project.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Course creation and updates
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 200, message = "Course title must be between 3 and 200 characters")
    private String title;

    @NotBlank(message = "Course description is required")
    @Size(min = 10, message = "Course description must be at least 10 characters")
    private String description;

    private org.springframework.web.multipart.MultipartFile imageFile;
    private String image;
}
