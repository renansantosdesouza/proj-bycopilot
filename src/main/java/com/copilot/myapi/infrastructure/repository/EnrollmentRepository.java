package com.copilot.myapi.infrastructure.repository;

import com.copilot.myapi.infrastructure.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByStudentId(Long studentId);
    List<EnrollmentEntity> findByCourseId(Long courseId);
    Optional<EnrollmentEntity> findByStudentIdAndCourseId(Long studentId, Long courseId);
}