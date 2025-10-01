package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.LessonDTO;
import com.copilot.myapi.infrastructure.entity.LessonEntity;
import com.copilot.myapi.infrastructure.repository.ModuleRepository;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    private final ModuleRepository moduleRepository;

    public LessonMapper(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public LessonDTO toDTO(LessonEntity entity) {
        if (entity == null) return null;
        
        return new LessonDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getContent(),
            entity.getVideoUrl(),
            entity.getDurationMinutes(),
            entity.getOrderNumber(),
            entity.getModule().getId()
        );
    }

    public LessonEntity toEntity(LessonDTO dto) {
        if (dto == null) return null;
        
        LessonEntity entity = new LessonEntity();
        entity.setId(dto.id());
        entity.setTitle(dto.title());
        entity.setContent(dto.content());
        entity.setVideoUrl(dto.videoUrl());
        entity.setDurationMinutes(dto.durationMinutes());
        entity.setOrderNumber(dto.orderNumber());
        
        moduleRepository.findById(dto.moduleId())
            .ifPresent(entity::setModule);
        
        return entity;
    }
}