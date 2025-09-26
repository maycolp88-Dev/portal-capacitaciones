package com.portal.controller;

import com.portal.model.ModuleProgress;
import com.portal.service.ModuleProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
public class ModuleProgressController {

    private final ModuleProgressService moduleProgressService;

    public ModuleProgressController(ModuleProgressService moduleProgressService) {
        this.moduleProgressService = moduleProgressService;
    }

    @PostMapping("/{moduleId}/start")
    public ResponseEntity<ModuleProgress> startModule(@PathVariable Long moduleId, @RequestParam Long userId) {
        return ResponseEntity.ok(moduleProgressService.startModule(userId, moduleId));
    }

    @PostMapping("/{moduleId}/complete")
    public ResponseEntity<ModuleProgress> completeModule(@PathVariable Long moduleId, @RequestParam Long userId) {
        return ResponseEntity.ok(moduleProgressService.completeModule(userId, moduleId));
    }
}
