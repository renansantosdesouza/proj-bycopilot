package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "INSTRUCTORS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(length = 1000)
    private String bio;
    
    @Column(name = "expertise_areas")
    private String expertiseAreas;
    
    @OneToMany(mappedBy = "instructor")
    private List<CourseEntity> courses;
}