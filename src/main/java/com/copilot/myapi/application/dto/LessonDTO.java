package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.*;

public record LessonDTO(
    Long id,
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title,
    
    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content must not exceed 2000 characters")
    String content,
    
    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$", 
            message = "Invalid video URL format")
    String videoUrl,
    
    @Positive(message = "Duration must be positive")
    Integer durationMinutes,
    
    @NotNull(message = "Order number is required")
    @Min(value = 1, message = "Order number must be positive")
    Integer orderNumber,
    
    @NotNull(message = "Module ID is required")
    Long moduleId
) {}