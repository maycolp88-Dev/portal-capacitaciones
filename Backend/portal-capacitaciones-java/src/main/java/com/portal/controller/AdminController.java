package com.portal.controller;

import com.portal.model.Course;
import com.portal.service.CourseService;
import com.portal.service.UserService;
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
    private CourseService courseService;

    @Autowired
    private UserService userService;

    // validar que el userId sea admin
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
}
