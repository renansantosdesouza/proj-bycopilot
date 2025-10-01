package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.*;

public record InstructorDTO(
    Long id,
    
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,
    
    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    String bio,
    
    String expertiseAreas
) {}