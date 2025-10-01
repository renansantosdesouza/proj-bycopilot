package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.CourseDTO;
import com.copilot.myapi.application.dto.InstructorDTO;
import com.copilot.myapi.application.mapper.CourseMapper;
import com.copilot.myapi.infrastructure.entity.CourseEntity;
import com.copilot.myapi.infrastructure.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseController courseController;

    private CourseDTO courseDTO;
    private CourseEntity courseEntity;
    private InstructorDTO instructorDTO;

    @BeforeEach
    void setUp() {
        instructorDTO = new InstructorDTO(1L, "John Doe", "john@example.com", "Expert Teacher", "Java");
        courseDTO = new CourseDTO(1L, "Java Course", "Complete Java Course", 
            new BigDecimal("99.99"), 40, null, null, instructorDTO, List.of());

        courseEntity = new CourseEntity();
        courseEntity.setId(1L);
        courseEntity.setTitle("Java Course");
        courseEntity.setDescription("Complete Java Course");
        courseEntity.setPrice(new BigDecimal("99.99"));
        courseEntity.setDurationHours(40);
    }

    @Test
    void shouldCreateCourse() {
        when(courseMapper.toEntity(any(CourseDTO.class))).thenReturn(courseEntity);
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
        when(courseMapper.toDTO(any(CourseEntity.class))).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseController.createCourse(courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(courseRepository).save(any(CourseEntity.class));
    }

    @Test
    void shouldGetCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        when(courseMapper.toDTO(courseEntity)).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseController.getCourseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(courseDTO, response.getBody());
    }

    @Test
    void shouldReturnNotFoundForInvalidId() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<CourseDTO> response = courseController.getCourseById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(courseEntity));
        when(courseMapper.toDTO(any(CourseEntity.class))).thenReturn(courseDTO);

        ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldUpdateCourse() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(courseMapper.toEntity(any(CourseDTO.class))).thenReturn(courseEntity);
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
        when(courseMapper.toDTO(any(CourseEntity.class))).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseController.updateCourse(1L, courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(courseRepository).save(any(CourseEntity.class));
    }

    @Test
    void shouldDeleteCourse() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = courseController.deleteCourse(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseRepository).deleteById(1L);
    }
}