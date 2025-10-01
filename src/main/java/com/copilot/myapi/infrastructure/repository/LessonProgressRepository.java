package com.copilot.myapi.infrastructure.repository;

import com.copilot.myapi.infrastructure.entity.LessonProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgressEntity, Long> {
    List<LessonProgressEntity> findByStudentId(Long studentId);
    List<LessonProgressEntity> findByLessonId(Long lessonId);
    Optional<LessonProgressEntity> findByStudentIdAndLessonId(Long studentId, Long lessonId);
    long countByStudentIdAndCompletedTrue(Long studentId);
}