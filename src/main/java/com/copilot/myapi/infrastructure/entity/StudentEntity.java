package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;
    
    @OneToMany(mappedBy = "student")
    private List<EnrollmentEntity> enrollments;
    
    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
    }
}