package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.InstructorDTO;
import com.copilot.myapi.application.mapper.InstructorMapper;
import com.copilot.myapi.infrastructure.repository.InstructorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instructors")
@Tag(name = "Instructors", description = "Instructor management APIs")
public class InstructorController {
    
    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    public InstructorController(InstructorRepository instructorRepository, InstructorMapper instructorMapper) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
    }

    @PostMapping
    @Operation(summary = "Create instructor", description = "Creates a new instructor")
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorDTO instructorDTO) {
        if (instructorRepository.existsByEmail(instructorDTO.email())) {
            return ResponseEntity.badRequest().build();
        }
        var entity = instructorMapper.toEntity(instructorDTO);
        var savedEntity = instructorRepository.save(entity);
        return ResponseEntity.ok(instructorMapper.toDTO(savedEntity));
    }

    @GetMapping
    @Operation(summary = "List instructors", description = "Get all instructors")
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        var instructors = instructorRepository.findAll();
        var dtos = instructors.stream()
                .map(instructorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get instructor", description = "Get instructor by ID")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        return instructorRepository.findById(id)
                .map(instructorMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update instructor", description = "Update an existing instructor")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable Long id, 
            @Valid @RequestBody InstructorDTO instructorDTO) {
        if (!instructorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var entity = instructorMapper.toEntity(instructorDTO);
        entity.setId(id);
        var updatedEntity = instructorRepository.save(entity);
        return ResponseEntity.ok(instructorMapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete instructor", description = "Delete an instructor")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        if (!instructorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        instructorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}