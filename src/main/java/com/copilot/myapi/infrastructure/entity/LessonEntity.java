package com.copilot.myapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LESSONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String content;
    
    @Column(name = "video_url")
    private String videoUrl;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    
    @Column(name = "order_number")
    private Integer orderNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleEntity module;
}