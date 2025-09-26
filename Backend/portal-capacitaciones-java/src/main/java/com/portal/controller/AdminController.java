package com.portal.controller;

import com.portal.model.Course;
import com.portal.repository.ModuleRepository;
import com.portal.service.ICourseService;
import com.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModuleRepository moduleRepo;


    private boolean isAdmin(Integer userId) {
        return userService.findById(userId)
                .map(u -> u.isAdmin())
                .orElse(false);
    }

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@RequestBody Course course, @RequestParam Integer userId) {
        if (!isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Solo administradores pueden crear cursos"));
        }

        return ResponseEntity.ok(courseService.saveCourse(course));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id, @RequestParam Integer userId) {
        if (!isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Solo administradores pueden eliminar cursos"));
        }
        courseService.deleteCourse(Long.valueOf(id));
        return ResponseEntity.ok(Map.of("message", "Curso eliminado"));
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> addModuleToCourse(
            @PathVariable Long courseId,
            @RequestBody com.portal.model.Module module,
            @RequestParam Integer userId) {

        if (!isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Solo administradores pueden agregar módulos"));
        }

        return ResponseEntity.ok(courseService.addModule(courseId, module));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable Long courseId,
                                          @PathVariable Long moduleId,
                                          @RequestParam Integer userId) {
        if (!isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Solo administradores pueden eliminar módulos"));
        }

        moduleRepo.findById(moduleId).ifPresent(m -> {
            if (m.getCourse().getId().equals(courseId)) {
                moduleRepo.delete(m);
            }
        });

        return ResponseEntity.ok(Map.of("message", "Módulo eliminado"));
    }

}

