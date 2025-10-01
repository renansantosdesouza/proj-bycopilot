package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.CourseDTO;
import com.copilot.myapi.infrastructure.entity.CourseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    
    private final ModuleMapper moduleMapper;
    private final InstructorMapper instructorMapper;

    public CourseMapper(ModuleMapper moduleMapper, InstructorMapper instructorMapper) {
        this.moduleMapper = moduleMapper;
        this.instructorMapper = instructorMapper;
    }

    public CourseDTO toDTO(CourseEntity entity) {
        if (entity == null) return null;
        
        return new CourseDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getPrice(),
            entity.getDurationHours(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            instructorMapper.toDTO(entity.getInstructor()),
            entity.getModules() != null
                ? entity.getModules().stream()
                    .map(moduleMapper::toDTO)
                    .collect(Collectors.toList())
                : new ArrayList<>()
        );
    }

    public CourseEntity toEntity(CourseDTO dto) {
        if (dto == null) return null;

        CourseEntity entity = new CourseEntity();
        entity.setId(dto.id());
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setDurationHours(dto.durationHours());
        entity.setInstructor(instructorMapper.toEntity(dto.instructor()));
        
        if (dto.modules() != null) {
            entity.setModules(dto.modules().stream()
                .map(moduleMapper::toEntity)
                .peek(module -> module.setCourse(entity))
                .collect(Collectors.toList()));
        }
        
        return entity;
    }
}