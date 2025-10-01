package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.ModuleDTO;
import com.copilot.myapi.infrastructure.entity.CourseEntity;
import com.copilot.myapi.infrastructure.entity.ModuleEntity;
import com.copilot.myapi.infrastructure.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ModuleMapperTest {

    @Mock
    private CourseRepository courseRepository;
    
    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private ModuleMapper moduleMapper;

    private ModuleEntity moduleEntity;
    private ModuleDTO moduleDTO;
    private CourseEntity courseEntity;

    @BeforeEach
    void setUp() {
        courseEntity = new CourseEntity();
        courseEntity.setId(1L);
        courseEntity.setTitle("Java Course");

        moduleEntity = new ModuleEntity();
        moduleEntity.setId(1L);
        moduleEntity.setTitle("Module 1");
        moduleEntity.setDescription("First Module");
        moduleEntity.setOrderNumber(1);
        moduleEntity.setCourse(courseEntity);
        moduleEntity.setLessons(new ArrayList<>());

        moduleDTO = new ModuleDTO(1L, "Module 1", "First Module", 1, 1L, new ArrayList<>());
    }

    @Test
    void shouldMapEntityToDTO() {
        ModuleDTO result = moduleMapper.toDTO(moduleEntity);

        assertNotNull(result);
        assertEquals(moduleEntity.getId(), result.id());
        assertEquals(moduleEntity.getTitle(), result.title());
        assertEquals(moduleEntity.getDescription(), result.description());
        assertEquals(moduleEntity.getOrderNumber(), result.orderNumber());
        assertEquals(moduleEntity.getCourse().getId(), result.courseId());
    }

    @Test
    void shouldMapDTOToEntity() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseEntity));

        ModuleEntity result = moduleMapper.toEntity(moduleDTO);

        assertNotNull(result);
        assertEquals(moduleDTO.id(), result.getId());
        assertEquals(moduleDTO.title(), result.getTitle());
        assertEquals(moduleDTO.description(), result.getDescription());
        assertEquals(moduleDTO.orderNumber(), result.getOrderNumber());
        assertNotNull(result.getCourse());
    }

    @Test
    void shouldHandleNullEntity() {
        assertNull(moduleMapper.toDTO(null));
    }

    @Test
    void shouldHandleNullDTO() {
        assertNull(moduleMapper.toEntity(null));
    }
}