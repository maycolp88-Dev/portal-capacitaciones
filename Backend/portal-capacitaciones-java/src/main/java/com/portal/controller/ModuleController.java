package com.portal.controller;

import com.portal.model.Module;
import com.portal.repository.ModuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    private final ModuleRepository moduleRepository;

    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModule(@PathVariable Long id) {
        return moduleRepository.findById(id)
                .map(m -> {
                    Map<String,Object> dto = new HashMap<>();
                    dto.put("id", m.getId());
                    dto.put("title", m.getTitle());
                    dto.put("description", m.getDescription());
                    dto.put("orderIndex", m.getOrderIndex());
                    dto.put("courseId", m.getCourse().getId());
                    dto.put("content", m.getContent());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}


