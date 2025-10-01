package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.CourseDTO;
import com.copilot.myapi.application.mapper.CourseMapper;
import com.copilot.myapi.infrastructure.repository.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Course management APIs")
public class CourseController {
    
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseController(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @PostMapping
    @Operation(summary = "Create course", description = "Creates a new course")
    @ApiResponse(responseCode = "200", description = "Course created successfully")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        var entity = courseMapper.toEntity(courseDTO);
        var savedEntity = courseRepository.save(entity);
        return ResponseEntity.ok(courseMapper.toDTO(savedEntity));
    }

    @GetMapping
    @Operation(summary = "List courses", description = "Get all courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        var courses = courseRepository.findAll();
        var dtos = courses.stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course", description = "Get course by ID")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "Get instructor courses", description = "Get courses by instructor ID")
    public ResponseEntity<List<CourseDTO>> getCoursesByInstructor(@PathVariable Long instructorId) {
        var courses = courseRepository.findByInstructorId(instructorId);
        var dtos = courses.stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update an existing course")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var entity = courseMapper.toEntity(courseDTO);
        entity.setId(id);
        var updatedEntity = courseRepository.save(entity);
        return ResponseEntity.ok(courseMapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}