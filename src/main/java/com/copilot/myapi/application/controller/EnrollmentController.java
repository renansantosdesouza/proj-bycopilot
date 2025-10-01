package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.EnrollmentDTO;
import com.copilot.myapi.application.mapper.EnrollmentMapper;
import com.copilot.myapi.infrastructure.repository.EnrollmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments", description = "Course enrollment management APIs")
public class EnrollmentController {
    
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentController(EnrollmentRepository enrollmentRepository, EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @PostMapping
    @Operation(summary = "Create enrollment", description = "Enroll a student in a course")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        var existingEnrollment = enrollmentRepository
            .findByStudentIdAndCourseId(enrollmentDTO.studentId(), enrollmentDTO.courseId());
        if (existingEnrollment.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        var entity = enrollmentMapper.toEntity(enrollmentDTO);
        var savedEntity = enrollmentRepository.save(entity);
        return ResponseEntity.ok(enrollmentMapper.toDTO(savedEntity));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get student enrollments", description = "Get all enrollments for a student")
    public ResponseEntity<List<EnrollmentDTO>> getStudentEnrollments(@PathVariable Long studentId) {
        var enrollments = enrollmentRepository.findByStudentId(studentId);
        var dtos = enrollments.stream()
                .map(enrollmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get course enrollments", description = "Get all enrollments for a course")
    public ResponseEntity<List<EnrollmentDTO>> getCourseEnrollments(@PathVariable Long courseId) {
        var enrollments = enrollmentRepository.findByCourseId(courseId);
        var dtos = enrollments.stream()
                .map(enrollmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/progress")
    @Operation(summary = "Update progress", description = "Update enrollment progress")
    public ResponseEntity<EnrollmentDTO> updateProgress(@PathVariable Long id, 
            @RequestParam Integer progressPercentage) {
        return enrollmentRepository.findById(id)
                .map(entity -> {
                    entity.setProgressPercentage(progressPercentage);
                    if (progressPercentage == 100) {
                        entity.setCompletedAt(java.time.LocalDateTime.now());
                    }
                    return enrollmentRepository.save(entity);
                })
                .map(enrollmentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel enrollment", description = "Cancel a course enrollment")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long id) {
        if (!enrollmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enrollmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}