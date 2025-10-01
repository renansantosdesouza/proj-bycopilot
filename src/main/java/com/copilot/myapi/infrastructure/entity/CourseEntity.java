package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "COURSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "duration_hours")
    private Integer durationHours;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private InstructorEntity instructor;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @OrderBy("orderNumber ASC")
    private List<ModuleEntity> modules;
    
    @OneToMany(mappedBy = "course")
    private List<EnrollmentEntity> enrollments;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}