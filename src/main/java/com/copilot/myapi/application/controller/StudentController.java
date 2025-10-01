package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.StudentDTO;
import com.copilot.myapi.application.mapper.StudentMapper;
import com.copilot.myapi.infrastructure.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Student management APIs")
public class StudentController {
    
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentController(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @PostMapping
    @Operation(summary = "Create student", description = "Creates a new student")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        if (studentRepository.existsByEmail(studentDTO.email())) {
            return ResponseEntity.badRequest().build();
        }
        var entity = studentMapper.toEntity(studentDTO);
        var savedEntity = studentRepository.save(entity);
        return ResponseEntity.ok(studentMapper.toDTO(savedEntity));
    }

    @GetMapping
    @Operation(summary = "List students", description = "Get all students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        var students = studentRepository.findAll();
        var dtos = students.stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student", description = "Get student by ID")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update an existing student")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, 
            @Valid @RequestBody StudentDTO studentDTO) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var entity = studentMapper.toEntity(studentDTO);
        entity.setId(id);
        var updatedEntity = studentRepository.save(entity);
        return ResponseEntity.ok(studentMapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Delete a student")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}