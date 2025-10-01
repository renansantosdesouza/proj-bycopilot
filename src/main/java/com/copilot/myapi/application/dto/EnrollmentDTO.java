package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record EnrollmentDTO(
    Long id,
    
    @NotNull(message = "Student ID is required")
    Long studentId,
    
    @NotNull(message = "Course ID is required")
    Long courseId,
    
    LocalDateTime enrolledAt,
    
    LocalDateTime completedAt,
    
    @Min(value = 0, message = "Progress cannot be negative")
    @Max(value = 100, message = "Progress cannot exceed 100%")
    Integer progressPercentage
) {}