package com.copilot.myapi.application.dto;

import jakarta.validation.constraints.*;

public record AddressDTO(
    Long id,
    
    @NotBlank(message = "Street is required")
    String street,
    
    @NotBlank(message = "Number is required")
    String number,
    
    String complement,
    
    @NotBlank(message = "Neighborhood is required")
    String neighborhood,
    
    @NotBlank(message = "City is required")
    String city,
    
    @NotBlank(message = "State is required")
    @Size(min = 2, max = 2, message = "State must be 2 characters")
    String state,
    
    @NotBlank(message = "ZIP code is required")
    @Pattern(regexp = "\\d{8}|\\d{5}-\\d{3}", message = "Invalid ZIP code format")
    String zipCode
) {}