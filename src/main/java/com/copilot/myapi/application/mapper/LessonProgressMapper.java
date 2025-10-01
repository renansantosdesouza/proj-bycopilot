package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.LessonProgressDTO;
import com.copilot.myapi.infrastructure.entity.LessonProgressEntity;
import com.copilot.myapi.infrastructure.repository.LessonRepository;
import com.copilot.myapi.infrastructure.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class LessonProgressMapper {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    public LessonProgressMapper(StudentRepository studentRepository, LessonRepository lessonRepository) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
    }

    public LessonProgressDTO toDTO(LessonProgressEntity entity) {
        if (entity == null) return null;

        return new LessonProgressDTO(
            entity.getId(),
            entity.getStudent().getId(),
            entity.getLesson().getId(),
            entity.getWatchedAt(),
            entity.isCompleted(),
            entity.getNotes()
        );
    }

    public LessonProgressEntity toEntity(LessonProgressDTO dto) {
        if (dto == null) return null;

        LessonProgressEntity entity = new LessonProgressEntity();
        entity.setId(dto.id());
        entity.setCompleted(dto.completed());
        entity.setNotes(dto.notes());

        studentRepository.findById(dto.studentId())
            .ifPresent(entity::setStudent);
        
        lessonRepository.findById(dto.lessonId())
            .ifPresent(entity::setLesson);

        return entity;
    }
}