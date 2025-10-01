package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENROLLMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;
    
    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "progress_percentage")
    private Integer progressPercentage = 0;
    
    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
    }
}