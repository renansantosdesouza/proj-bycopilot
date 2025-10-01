package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record LessonProgressDTO(
    Long id,
    
    @NotNull(message = "Student ID is required")
    Long studentId,
    
    @NotNull(message = "Lesson ID is required")
    Long lessonId,
    
    LocalDateTime watchedAt,
    
    boolean completed,
    
    String notes
) {}