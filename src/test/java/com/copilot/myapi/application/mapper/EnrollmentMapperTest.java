package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.EnrollmentDTO;
import com.copilot.myapi.infrastructure.entity.CourseEntity;
import com.copilot.myapi.infrastructure.entity.EnrollmentEntity;
import com.copilot.myapi.infrastructure.entity.StudentEntity;
import com.copilot.myapi.infrastructure.repository.CourseRepository;
import com.copilot.myapi.infrastructure.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentMapperTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentMapper enrollmentMapper;

    private EnrollmentEntity enrollmentEntity;
    private EnrollmentDTO enrollmentDTO;
    private StudentEntity studentEntity;
    private CourseEntity courseEntity;

    @BeforeEach
    void setUp() {
        studentEntity = new StudentEntity();
        studentEntity.setId(1L);
        studentEntity.setName("John Doe");

        courseEntity = new CourseEntity();
        courseEntity.setId(1L);
        courseEntity.setTitle("Java Course");

        enrollmentEntity = new EnrollmentEntity();
        enrollmentEntity.setId(1L);
        enrollmentEntity.setStudent(studentEntity);
        enrollmentEntity.setCourse(courseEntity);
        enrollmentEntity.setEnrolledAt(LocalDateTime.now());
        enrollmentEntity.setProgressPercentage(0);

        enrollmentDTO = new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), null, 0);
    }

    @Test
    void shouldMapEntityToDTO() {
        EnrollmentDTO dto = enrollmentMapper.toDTO(enrollmentEntity);

        assertNotNull(dto);
        assertEquals(enrollmentEntity.getId(), dto.id());
        assertEquals(enrollmentEntity.getStudent().getId(), dto.studentId());
        assertEquals(enrollmentEntity.getCourse().getId(), dto.courseId());
        assertEquals(enrollmentEntity.getProgressPercentage(), dto.progressPercentage());
    }

    @Test
    void shouldMapDTOToEntity() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentEntity));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseEntity));

        EnrollmentEntity entity = enrollmentMapper.toEntity(enrollmentDTO);

        assertNotNull(entity);
        assertEquals(enrollmentDTO.id(), entity.getId());
        assertEquals(enrollmentDTO.progressPercentage(), entity.getProgressPercentage());
        assertNotNull(entity.getStudent());
        assertNotNull(entity.getCourse());
    }

    @Test
    void shouldHandleNullEntity() {
        assertNull(enrollmentMapper.toDTO(null));
    }

    @Test
    void shouldHandleNullDTO() {
        assertNull(enrollmentMapper.toEntity(null));
    }
}