package com.copilot.myapi.infrastructure.repository;

import com.copilot.myapi.infrastructure.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByCourseId(Long courseId);
    List<LessonEntity> findByCourseIdOrderByOrderNumberAsc(Long courseId);
}