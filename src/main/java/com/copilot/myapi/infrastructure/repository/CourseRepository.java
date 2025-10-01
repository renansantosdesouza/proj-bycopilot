package com.copilot.myapi.infrastructure.repository;

import com.copilot.myapi.infrastructure.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByInstructorId(Long instructorId);
    List<CourseEntity> findByTitleContainingIgnoreCase(String title);
}