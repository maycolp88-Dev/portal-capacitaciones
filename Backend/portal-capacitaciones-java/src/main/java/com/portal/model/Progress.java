package com.portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "progress")
@Data
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long courseId;

    private Long moduleId;

    private String status;
    private String updatedAt;

    public Progress() {}

    public Progress(Long userId, Long courseId, Long moduleId, String status, String updatedAt) {
        this.userId = userId;
        this.courseId = courseId;
        this.moduleId = moduleId;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public Progress(Long userId, Long courseId, String status, String updatedAt) {
        this.userId = userId;
        this.courseId = courseId;
        this.moduleId = null;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
