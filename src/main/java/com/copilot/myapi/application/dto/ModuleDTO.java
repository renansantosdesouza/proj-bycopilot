package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record ModuleDTO(
    Long id,
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title,
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    String description,
    
    @NotNull(message = "Order number is required")
    @Min(value = 1, message = "Order number must be positive")
    Integer orderNumber,
    
    Long courseId,
    
    List<LessonDTO> lessons
) {}