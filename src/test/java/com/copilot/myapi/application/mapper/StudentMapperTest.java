package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.StudentDTO;
import com.copilot.myapi.infrastructure.entity.StudentEntity;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private final StudentMapper studentMapper = new StudentMapper();

    @Test
    void shouldMapEntityToDTO() {
        StudentEntity entity = new StudentEntity();
        entity.setId(1L);
        entity.setName("John Doe");
        entity.setEmail("john@example.com");
        entity.setPassword("secret123");
        entity.setRegisteredAt(LocalDateTime.now());

        StudentDTO dto = studentMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getEmail(), dto.email());
        assertNull(dto.password()); // Password should not be included in DTO
        assertEquals(entity.getRegisteredAt(), dto.registeredAt());
    }

    @Test
    void shouldMapDTOToEntity() {
        LocalDateTime now = LocalDateTime.now();
        StudentDTO dto = new StudentDTO(1L, "John Doe", "john@example.com", "secret123", now);

        StudentEntity entity = studentMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.password(), entity.getPassword());
    }

    @Test
    void shouldHandleNullEntity() {
        assertNull(studentMapper.toDTO(null));
    }

    @Test
    void shouldHandleNullDTO() {
        assertNull(studentMapper.toEntity(null));
    }
}