package com.copilot.myapi.infrastructure.repository;

import com.copilot.myapi.infrastructure.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
    List<ModuleEntity> findByCourseIdOrderByOrderNumberAsc(Long courseId);
}