package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.LessonProgressDTO;
import com.copilot.myapi.application.mapper.LessonProgressMapper;
import com.copilot.myapi.infrastructure.entity.*;
import com.copilot.myapi.infrastructure.repository.EnrollmentRepository;
import com.copilot.myapi.infrastructure.repository.LessonProgressRepository;
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
class LessonProgressControllerTest {

    @Mock
    private LessonProgressRepository progressRepository;

    @Mock
    private LessonProgressMapper progressMapper;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private LessonProgressController progressController;

    private LessonProgressDTO progressDTO;
    private LessonProgressEntity progressEntity;
    private CourseEntity courseEntity;
    private ModuleEntity moduleEntity;
    private LessonEntity lessonEntity;
    private EnrollmentEntity enrollmentEntity;

    @BeforeEach
    void setUp() {
        courseEntity = new CourseEntity();
        courseEntity.setId(1L);
        courseEntity.setTitle("Java Course");

        moduleEntity = new ModuleEntity();
        moduleEntity.setId(1L);
        moduleEntity.setTitle("Module 1");
        moduleEntity.setCourse(courseEntity);

        lessonEntity = new LessonEntity();
        lessonEntity.setId(1L);
        lessonEntity.setTitle("Lesson 1");
        lessonEntity.setModule(moduleEntity);

        progressDTO = new LessonProgressDTO(1L, 1L, 1L, LocalDateTime.now(), true, "Good lesson");
        
        progressEntity = new LessonProgressEntity();
        progressEntity.setId(1L);
        progressEntity.setCompleted(true);
        progressEntity.setLesson(lessonEntity);
        
        enrollmentEntity = new EnrollmentEntity();
        enrollmentEntity.setId(1L);
        enrollmentEntity.setCourse(courseEntity);
    }

    @Test
    void shouldMarkLessonAsWatched() {
        when(progressRepository.findByStudentIdAndLessonId(anyLong(), anyLong()))
            .thenReturn(Optional.empty());
        when(progressMapper.toEntity(any(LessonProgressDTO.class)))
            .thenReturn(progressEntity);
        when(progressRepository.save(any(LessonProgressEntity.class)))
            .thenReturn(progressEntity);
        when(progressMapper.toDTO(any(LessonProgressEntity.class)))
            .thenReturn(progressDTO);
        when(enrollmentRepository.findByStudentIdAndCourseId(anyLong(), anyLong()))
            .thenReturn(Optional.of(enrollmentEntity));

        ResponseEntity<LessonProgressDTO> response = progressController.markLessonAsWatched(progressDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(progressRepository).save(any(LessonProgressEntity.class));
    }

    @Test
    void shouldUpdateExistingProgress() {
        when(progressRepository.findByStudentIdAndLessonId(anyLong(), anyLong()))
            .thenReturn(Optional.of(progressEntity));
        when(progressRepository.save(any(LessonProgressEntity.class)))
            .thenReturn(progressEntity);
        when(progressMapper.toDTO(any(LessonProgressEntity.class)))
            .thenReturn(progressDTO);

        ResponseEntity<LessonProgressDTO> response = progressController.markLessonAsWatched(progressDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(progressRepository).save(progressEntity);
    }

    @Test
    void shouldGetStudentCourseProgress() {
        when(progressRepository.findByStudentId(anyLong()))
            .thenReturn(List.of(progressEntity));
        when(progressMapper.toDTO(any(LessonProgressEntity.class)))
            .thenReturn(progressDTO);

        ResponseEntity<List<LessonProgressDTO>> response = 
            progressController.getStudentCourseProgress(1L, 1L);

        assertAll(
            () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> assertNotNull(response.getBody()),
            () -> assertEquals(1, Optional.ofNullable(response.getBody())
                    .map(List::size)
                    .orElse(0))
        );
    }
}