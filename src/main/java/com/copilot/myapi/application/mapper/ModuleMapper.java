package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.ModuleDTO;
import com.copilot.myapi.infrastructure.entity.ModuleEntity;
import com.copilot.myapi.infrastructure.repository.CourseRepository;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ModuleMapper {
    
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    public ModuleMapper(CourseRepository courseRepository, LessonMapper lessonMapper) {
        this.courseRepository = courseRepository;
        this.lessonMapper = lessonMapper;
    }

    public ModuleDTO toDTO(ModuleEntity entity) {
        if (entity == null) return null;

        return new ModuleDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getOrderNumber(),
            entity.getCourse().getId(),
            entity.getLessons().stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList())
        );
    }

    public ModuleEntity toEntity(ModuleDTO dto) {
        if (dto == null) return null;

        ModuleEntity entity = new ModuleEntity();
        entity.setId(dto.id());
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setOrderNumber(dto.orderNumber());

        courseRepository.findById(dto.courseId())
            .ifPresent(entity::setCourse);

        if (dto.lessons() != null) {
            entity.setLessons(dto.lessons().stream()
                .map(lessonMapper::toEntity)
                .peek(lesson -> lesson.setModule(entity))
                .collect(Collectors.toList()));
        }

        return entity;
    }
}