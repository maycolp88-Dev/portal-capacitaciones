package com.portal.controller;

import com.portal.model.ModuleProgress;
import com.portal.service.IModuleProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
public class ModuleProgressController {

    private final IModuleProgressService moduleProgressService;

    public ModuleProgressController(IModuleProgressService moduleProgressService) {
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
