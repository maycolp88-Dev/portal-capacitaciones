package com.portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String description;
    private int orderIndex;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
}

