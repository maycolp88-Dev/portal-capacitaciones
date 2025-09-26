package com.portal.controller;

import com.portal.model.Module;
import com.portal.model.Progress;
import com.portal.DTO.CourseDTO;
import com.portal.service.CourseService;
import com.portal.repository.ModuleRepository;
import com.portal.repository.ProgressRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;
    private final ModuleRepository moduleRepo;
    private final ProgressRepository progressRepo;

    public CourseController(CourseService courseService,
                            ModuleRepository moduleRepo,
                            ProgressRepository progressRepo) {
        this.courseService = courseService;
        this.moduleRepo = moduleRepo;
        this.progressRepo = progressRepo;
    }

    /**
     * GET /api/courses?userId=1
     * userId es opcional: si no viene devolvemos la lista sin progreso (userId = 0)
     */
    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> listCourses(@RequestParam(required = false) Long userId) {
        try {
            Long uid = (userId == null) ? 0L : userId;
            List<CourseDTO> dtos = courseService.listCoursesWithModules(uid);
            return ResponseEntity.ok(dtos);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/courses/{courseId}/modules
     */
    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<Module>> getModules(@PathVariable Long courseId) {
        try {
            List<Module> modules = courseService.getModules(courseId);
            return ResponseEntity.ok(modules);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/courses/{courseId}/start  body: { "userId": 1 }
     */
    @PostMapping("/courses/{courseId}/start")
    public ResponseEntity<?> startCourse(@PathVariable Long courseId, @RequestBody Map<String, Long> body) {
        Long userId = body == null ? null : body.get("userId");
        if (userId == null) return ResponseEntity.badRequest().body(Map.of("error", "userId requerido"));

        try {
            Progress p = courseService.startCourse(courseId, userId);
            return ResponseEntity.ok(Map.of("message", "Curso iniciado", "courseProgress", p));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
        }
    }

    /**
     * POST /api/courses/{courseId}/complete  body: { "userId": 1 }
     * (marca todo el curso completo y genera badge)
     */
    @PostMapping("/courses/{courseId}/complete")
    public ResponseEntity<?> completeCourse(@PathVariable Long courseId, @RequestBody Map<String, Long> body) {
        Long userId = body == null ? null : body.get("userId");
        if (userId == null) return ResponseEntity.badRequest().body(Map.of("error", "userId requerido"));

        try {
            Progress p = courseService.completeCourse(courseId, userId);
            return ResponseEntity.ok(Map.of("message", "Curso completado", "courseProgress", p));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
        }
    }

    /**
     * POST /api/courses/{courseId}/modules/{moduleId}/complete  body: { "userId": 1 }
     * Marca módulo como completado; CourseService ya actualiza curso/badge si corresponde.
     */
    @PostMapping("/courses/{courseId}/modules/{moduleId}/complete")
    public ResponseEntity<?> completeModule(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @RequestBody Map<String, Long> body
    ) {
        Long userId = body == null ? null : body.get("userId");
        if (userId == null) return ResponseEntity.badRequest().body(Map.of("error", "userId requerido"));

        try {
            Progress p = courseService.completeModule(courseId, moduleId, userId);

            // calcular % (opcional, útil para frontend)
            long total = moduleRepo.countByCourseId(courseId);
            long completed = progressRepo.findByUserIdAndCourseId(userId, courseId).stream()
                    .filter(pr -> pr.getModuleId() != null && "completado".equals(pr.getStatus()))
                    .count();
            int percent = total > 0 ? (int) ((completed * 100.0) / total) : 0;

            return ResponseEntity.ok(Map.of("message", "Módulo completado", "avance", percent + "%", "moduleProgress", p));
        } catch (RuntimeException rex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", rex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
        }
    }
}
