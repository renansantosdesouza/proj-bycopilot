package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.LessonProgressDTO;
import com.copilot.myapi.application.mapper.LessonProgressMapper;
import com.copilot.myapi.infrastructure.entity.LessonProgressEntity;
import com.copilot.myapi.infrastructure.repository.LessonProgressRepository;
import com.copilot.myapi.infrastructure.repository.EnrollmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons/progress")
@Tag(name = "Lesson Progress", description = "Lesson progress tracking APIs")
public class LessonProgressController {
    
    private final LessonProgressRepository progressRepository;
    private final LessonProgressMapper progressMapper;
    private final EnrollmentRepository enrollmentRepository;

    public LessonProgressController(LessonProgressRepository progressRepository, 
                                  LessonProgressMapper progressMapper,
                                  EnrollmentRepository enrollmentRepository) {
        this.progressRepository = progressRepository;
        this.progressMapper = progressMapper;
        this.enrollmentRepository = enrollmentRepository;
    }

    @PostMapping
    @Operation(summary = "Mark lesson as watched", description = "Mark a lesson as watched by a student")
    public ResponseEntity<LessonProgressDTO> markLessonAsWatched(@RequestBody LessonProgressDTO progressDTO) {
        var existing = progressRepository.findByStudentIdAndLessonId(
            progressDTO.studentId(), progressDTO.lessonId());
        
        if (existing.isPresent()) {
            var entity = existing.get();
            entity.setCompleted(progressDTO.completed());
            var updated = progressRepository.save(entity);
            return ResponseEntity.ok(progressMapper.toDTO(updated));
        }

        var entity = progressMapper.toEntity(progressDTO);
        var saved = progressRepository.save(entity);
        
        // Update enrollment progress
        updateEnrollmentProgress(progressDTO.studentId(), saved.getLesson().getModule().getCourse().getId());
        
        return ResponseEntity.ok(progressMapper.toDTO(saved));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    @Operation(summary = "Get progress", description = "Get student's progress for a course")
    public ResponseEntity<List<LessonProgressDTO>> getStudentCourseProgress(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        var progress = progressRepository.findByStudentId(studentId).stream()
                .filter(p -> p.getLesson().getModule().getCourse().getId().equals(courseId))
                .map(progressMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(progress);
    }

    private void updateEnrollmentProgress(Long studentId, Long courseId) {
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
            .ifPresent(enrollment -> {
                long totalLessons = enrollment.getCourse().getModules().stream()
                    .mapToLong(module -> module.getLessons().size())
                    .sum();
                
                long watchedLessons = enrollment.getCourse().getModules().stream()
                    .flatMap(module -> module.getLessons().stream())
                    .filter(lesson -> progressRepository.findByStudentIdAndLessonId(studentId, lesson.getId())
                        .map(LessonProgressEntity::isCompleted)
                        .orElse(false))
                    .count();
                
                int progressPercentage = totalLessons > 0 
                    ? (int) ((watchedLessons * 100) / totalLessons)
                    : 0;
                    
                enrollment.setProgressPercentage(progressPercentage);
                
                if (progressPercentage == 100) {
                    enrollment.setCompletedAt(java.time.LocalDateTime.now());
                }
                
                enrollmentRepository.save(enrollment);
            });
    }
}