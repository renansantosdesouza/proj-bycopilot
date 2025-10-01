package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.EnrollmentDTO;
import com.copilot.myapi.infrastructure.entity.EnrollmentEntity;
import com.copilot.myapi.infrastructure.repository.CourseRepository;
import com.copilot.myapi.infrastructure.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentMapper(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public EnrollmentDTO toDTO(EnrollmentEntity entity) {
        if (entity == null) return null;
        
        return new EnrollmentDTO(
            entity.getId(),
            entity.getStudent().getId(),
            entity.getCourse().getId(),
            entity.getEnrolledAt(),
            entity.getCompletedAt(),
            entity.getProgressPercentage()
        );
    }

    public EnrollmentEntity toEntity(EnrollmentDTO dto) {
        if (dto == null) return null;
        
        EnrollmentEntity entity = new EnrollmentEntity();
        entity.setId(dto.id());
        
        studentRepository.findById(dto.studentId())
            .ifPresent(entity::setStudent);
        
        courseRepository.findById(dto.courseId())
            .ifPresent(entity::setCourse);
        
        entity.setProgressPercentage(dto.progressPercentage());
        entity.setCompletedAt(dto.completedAt());
        
        return entity;
    }
}