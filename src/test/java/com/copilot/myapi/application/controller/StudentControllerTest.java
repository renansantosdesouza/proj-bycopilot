package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.StudentDTO;
import com.copilot.myapi.application.mapper.StudentMapper;
import com.copilot.myapi.infrastructure.entity.StudentEntity;
import com.copilot.myapi.infrastructure.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentController studentController;

    private StudentDTO studentDTO;
    private StudentEntity studentEntity;

    @BeforeEach
    void setUp() {
        studentDTO = new StudentDTO(1L, "John Doe", "john@example.com", "password123", null);
        
        studentEntity = new StudentEntity();
        studentEntity.setId(1L);
        studentEntity.setName("John Doe");
        studentEntity.setEmail("john@example.com");
    }

    @Test
    void shouldCreateStudent() {
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentMapper.toEntity(any(StudentDTO.class))).thenReturn(studentEntity);
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);
        when(studentMapper.toDTO(any(StudentEntity.class))).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(studentRepository).save(any(StudentEntity.class));
    }

    @Test
    void shouldNotCreateStudentWithDuplicateEmail() {
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));
        when(studentMapper.toDTO(studentEntity)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.getStudentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDTO, response.getBody());
    }

    @Test
    void shouldReturnNotFoundForInvalidId() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<StudentDTO> response = studentController.getStudentById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(studentEntity));
        when(studentMapper.toDTO(any(StudentEntity.class))).thenReturn(studentDTO);

        ResponseEntity<List<StudentDTO>> response = studentController.getAllStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldDeleteStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentRepository).deleteById(1L);
    }
}