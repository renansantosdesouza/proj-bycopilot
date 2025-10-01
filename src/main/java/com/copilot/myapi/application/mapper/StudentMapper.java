package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.StudentDTO;
import com.copilot.myapi.infrastructure.entity.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDTO toDTO(StudentEntity entity) {
        if (entity == null) return null;
        
        return new StudentDTO(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            null, // Don't send password back in DTO
            entity.getRegisteredAt()
        );
    }

    public StudentEntity toEntity(StudentDTO dto) {
        if (dto == null) return null;
        
        StudentEntity entity = new StudentEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        if (dto.password() != null) {
            // In a real application, password should be hashed here
            entity.setPassword(dto.password());
        }
        return entity;
    }
}