package com.portal.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {
    private Long id;
    private String title;
    private String description;
    private int orderIndex;
    private String status;
    private String content;
}

