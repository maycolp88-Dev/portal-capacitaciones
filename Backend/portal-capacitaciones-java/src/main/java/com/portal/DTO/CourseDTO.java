package com.portal.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String module;
    private String description;
    private int progress;
    private List<ModuleDTO> modules;
    private String courseStatus;
}
