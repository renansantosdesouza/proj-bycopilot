package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CourseDTO(
    Long id,
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title,
    
    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    String description,
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    BigDecimal price,
    
    @Positive(message = "Duration must be positive")
    Integer durationHours,
    
    LocalDateTime createdAt,
    
    LocalDateTime updatedAt,
    
    @NotNull(message = "Instructor is required")
    InstructorDTO instructor,
    
    List<ModuleDTO> modules
) {}