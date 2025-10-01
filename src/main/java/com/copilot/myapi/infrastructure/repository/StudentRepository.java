package com.copilot.myapi.infrastructure.repository;

import com.copilot.myapi.infrastructure.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}