package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.CourseDTO;
import com.copilot.myapi.application.dto.InstructorDTO;
import com.copilot.myapi.application.dto.ModuleDTO;
import com.copilot.myapi.infrastructure.entity.CourseEntity;
import com.copilot.myapi.infrastructure.entity.InstructorEntity;
import com.copilot.myapi.infrastructure.entity.ModuleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseMapperTest {

    @Mock
    private ModuleMapper moduleMapper;

    @Mock
    private InstructorMapper instructorMapper;

    @InjectMocks
    private CourseMapper courseMapper;

    private CourseEntity courseEntity;
    private CourseDTO courseDTO;
    private InstructorEntity instructorEntity;
    private InstructorDTO instructorDTO;
    private ModuleEntity moduleEntity;
    private ModuleDTO moduleDTO;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        
        instructorEntity = new InstructorEntity();
        instructorEntity.setId(1L);
        instructorEntity.setName("John Doe");
        instructorEntity.setEmail("john@example.com");
        
        instructorDTO = new InstructorDTO(1L, "John Doe", "john@example.com", "Expert", "Java");

        moduleEntity = new ModuleEntity();
        moduleEntity.setId(1L);
        moduleEntity.setTitle("Module 1");
        
        moduleDTO = new ModuleDTO(1L, "Module 1", "First Module", 1, 1L, new ArrayList<>());

        courseEntity = new CourseEntity();
        courseEntity.setId(1L);
        courseEntity.setTitle("Java Course");
        courseEntity.setDescription("Complete Java Course");
        courseEntity.setPrice(new BigDecimal("99.99"));
        courseEntity.setDurationHours(40);
        courseEntity.setCreatedAt(now);
        courseEntity.setInstructor(instructorEntity);
        courseEntity.setModules(List.of(moduleEntity));

        courseDTO = new CourseDTO(1L, "Java Course", "Complete Java Course",
            new BigDecimal("99.99"), 40, now, null, instructorDTO, List.of(moduleDTO));
    }

    @Test
    void shouldMapEntityToDTO() {
        when(instructorMapper.toDTO(any(InstructorEntity.class))).thenReturn(instructorDTO);
        when(moduleMapper.toDTO(any(ModuleEntity.class))).thenReturn(moduleDTO);

        CourseDTO result = courseMapper.toDTO(courseEntity);

        assertNotNull(result);
        assertEquals(courseEntity.getId(), result.id());
        assertEquals(courseEntity.getTitle(), result.title());
        assertEquals(courseEntity.getDescription(), result.description());
        assertEquals(courseEntity.getPrice(), result.price());
        assertEquals(courseEntity.getDurationHours(), result.durationHours());
        assertEquals(courseEntity.getCreatedAt(), result.createdAt());
        assertNotNull(result.instructor());
        assertNotNull(result.modules());
        assertFalse(result.modules().isEmpty());
    }

    @Test
    void shouldMapDTOToEntity() {
        when(instructorMapper.toEntity(any(InstructorDTO.class))).thenReturn(instructorEntity);
        when(moduleMapper.toEntity(any(ModuleDTO.class))).thenReturn(moduleEntity);

        CourseEntity result = courseMapper.toEntity(courseDTO);

        assertNotNull(result);
        assertEquals(courseDTO.id(), result.getId());
        assertEquals(courseDTO.title(), result.getTitle());
        assertEquals(courseDTO.description(), result.getDescription());
        assertEquals(courseDTO.price(), result.getPrice());
        assertEquals(courseDTO.durationHours(), result.getDurationHours());
        assertNotNull(result.getInstructor());
        assertNotNull(result.getModules());
        assertFalse(result.getModules().isEmpty());
    }

    @Test
    void shouldHandleNullEntity() {
        assertNull(courseMapper.toDTO(null));
    }

    @Test
    void shouldHandleNullDTO() {
        assertNull(courseMapper.toEntity(null));
    }

    @Test
    void shouldHandleEmptyModulesList() {
        courseEntity.setModules(null);
        when(instructorMapper.toDTO(any(InstructorEntity.class))).thenReturn(instructorDTO);

        CourseDTO result = courseMapper.toDTO(courseEntity);

        assertNotNull(result);
        assertTrue(result.modules().isEmpty());
    }
}