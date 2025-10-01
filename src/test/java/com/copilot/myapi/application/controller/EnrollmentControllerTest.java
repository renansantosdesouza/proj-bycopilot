package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.EnrollmentDTO;
import com.copilot.myapi.application.mapper.EnrollmentMapper;
import com.copilot.myapi.infrastructure.entity.EnrollmentEntity;
import com.copilot.myapi.infrastructure.repository.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private EnrollmentDTO enrollmentDTO;
    private EnrollmentEntity enrollmentEntity;

    @BeforeEach
    void setUp() {
        enrollmentDTO = new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), null, 0);
        
        enrollmentEntity = new EnrollmentEntity();
        enrollmentEntity.setId(1L);
        enrollmentEntity.setProgressPercentage(0);
    }

    @Test
    void shouldCreateEnrollment() {
        when(enrollmentRepository.findByStudentIdAndCourseId(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(enrollmentMapper.toEntity(any(EnrollmentDTO.class))).thenReturn(enrollmentEntity);
        when(enrollmentRepository.save(any(EnrollmentEntity.class))).thenReturn(enrollmentEntity);
        when(enrollmentMapper.toDTO(any(EnrollmentEntity.class))).thenReturn(enrollmentDTO);

        ResponseEntity<EnrollmentDTO> response = enrollmentController.createEnrollment(enrollmentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(enrollmentRepository).save(any(EnrollmentEntity.class));
    }

    @Test
    void shouldNotCreateDuplicateEnrollment() {
        when(enrollmentRepository.findByStudentIdAndCourseId(anyLong(), anyLong()))
            .thenReturn(Optional.of(enrollmentEntity));

        ResponseEntity<EnrollmentDTO> response = enrollmentController.createEnrollment(enrollmentDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetStudentEnrollments() {
        when(enrollmentRepository.findByStudentId(anyLong())).thenReturn(List.of(enrollmentEntity));
        when(enrollmentMapper.toDTO(any(EnrollmentEntity.class))).thenReturn(enrollmentDTO);

        ResponseEntity<List<EnrollmentDTO>> response = enrollmentController.getStudentEnrollments(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldUpdateProgress() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollmentEntity));
        when(enrollmentRepository.save(any(EnrollmentEntity.class))).thenReturn(enrollmentEntity);
        when(enrollmentMapper.toDTO(any(EnrollmentEntity.class))).thenReturn(enrollmentDTO);

        ResponseEntity<EnrollmentDTO> response = enrollmentController.updateProgress(1L, 50);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(enrollmentRepository).save(any(EnrollmentEntity.class));
    }

    @Test
    void shouldSetCompletedAtWhenProgress100() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollmentEntity));
        when(enrollmentRepository.save(any(EnrollmentEntity.class))).thenReturn(enrollmentEntity);
        when(enrollmentMapper.toDTO(any(EnrollmentEntity.class))).thenReturn(enrollmentDTO);

        ResponseEntity<EnrollmentDTO> response = enrollmentController.updateProgress(1L, 100);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(enrollmentRepository).save(any(EnrollmentEntity.class));
    }

    @Test
    void shouldDeleteEnrollment() {
        when(enrollmentRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = enrollmentController.cancelEnrollment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(enrollmentRepository).deleteById(1L);
    }
}