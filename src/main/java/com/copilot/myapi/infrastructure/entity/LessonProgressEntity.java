package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "LESSON_PROGRESS",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lesson_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    @Column(name = "watched_at", nullable = false)
    private LocalDateTime watchedAt;

    @Column(name = "completed", nullable = false)
    private boolean completed;
    
    @Column(name = "notes", length = 1000)
    private String notes;

    @PrePersist
    protected void onCreate() {
        watchedAt = LocalDateTime.now();
    }
}