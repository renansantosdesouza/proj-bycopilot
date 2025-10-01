package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.InstructorDTO;
import com.copilot.myapi.infrastructure.entity.InstructorEntity;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public InstructorDTO toDTO(InstructorEntity entity) {
        if (entity == null) return null;
        
        return new InstructorDTO(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getBio(),
            entity.getExpertiseAreas()
        );
    }

    public InstructorEntity toEntity(InstructorDTO dto) {
        if (dto == null) return null;
        
        InstructorEntity entity = new InstructorEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setBio(dto.bio());
        entity.setExpertiseAreas(dto.expertiseAreas());
        return entity;
    }
}