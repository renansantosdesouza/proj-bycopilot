package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "MODULES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "order_number")
    private Integer orderNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;
    
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @OrderBy("orderNumber ASC")
    private List<LessonEntity> lessons;
}