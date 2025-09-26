package com.portal.service;

import com.portal.DTO.CourseDTO;
import com.portal.DTO.ModuleDTO;
import com.portal.model.Badge;
import com.portal.model.Course;
import com.portal.model.Module;
import com.portal.model.Progress;
import com.portal.repository.BadgeRepository;
import com.portal.repository.CourseRepository;
import com.portal.repository.ModuleRepository;
import com.portal.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final ProgressRepository progressRepo;
    private final BadgeRepository badgeRepo;
    private final ModuleRepository moduleRepo;

    public CourseService(CourseRepository courseRepo,
                         ProgressRepository progressRepo,
                         BadgeRepository badgeRepo,
                         ModuleRepository moduleRepository) {
        this.courseRepo = courseRepo;
        this.progressRepo = progressRepo;
        this.badgeRepo = badgeRepo;
        this.moduleRepo = moduleRepository;
    }

    // ========================
    // LISTAR CURSOS CON MÓDULOS Y PROGRESO
    // ========================
    public List<CourseDTO> listCoursesWithModules(Long userId) {
        List<Course> courses = courseRepo.findAllByOrderByModuleAscIdAsc();
        List<CourseDTO> dtos = new ArrayList<>();

        for (Course c : courses) {
            List<Module> modules = moduleRepo.findByCourseIdOrderByOrderIndex(c.getId());

            // convertir módulos a DTO con estado
            List<ModuleDTO> moduleDtos = new ArrayList<>();
            for (Module m : modules) {
                String status = progressRepo.findByUserIdAndCourseIdAndModuleId(userId, c.getId(), m.getId())
                        .map(Progress::getStatus)
                        .orElse("pendiente");
                moduleDtos.add(new ModuleDTO(
                        m.getId(),
                        m.getTitle(),
                        m.getDescription(),
                        m.getOrderIndex(),
                        status,
                        m.getContent()
                ));
            }

            // calcular % curso
            long total = modules.size();
            long completed = moduleDtos.stream().filter(md -> "completado".equals(md.getStatus())).count();
            int percent = (total > 0) ? (int) ((completed * 100.0) / total) : 0;

            // estado del curso
            String courseStatus = (completed == total && total > 0) ? "completado"
                    : (completed > 0 ? "en progreso" : "iniciado");

            CourseDTO dto = new CourseDTO(
                    c.getId(),
                    c.getTitle(),
                    c.getModule(),
                    c.getDescription(),
                    percent,
                    moduleDtos,
                    courseStatus
            );
            dtos.add(dto);
        }

        return dtos;
    }

    // ========================
    // INICIAR CURSO
    // ========================
    public Progress startCourse(Long courseId, Long userId) {
        String now = Instant.now().toString();

        // progreso del curso
        Progress courseProgress = progressRepo.findByUserIdAndCourseIdAndModuleIdIsNull(userId, courseId)
                .orElseGet(() -> {
                    Progress p = new Progress(userId, courseId, null, "iniciado", now);
                    return progressRepo.save(p);
                });

        // progreso de cada módulo (si no existe -> pendiente)
        List<Module> modules = moduleRepo.findByCourseIdOrderByOrderIndex(courseId);
        for (Module m : modules) {
            progressRepo.findByUserIdAndCourseIdAndModuleId(userId, courseId, m.getId())
                    .orElseGet(() -> {
                        Progress mp = new Progress(userId, courseId, m.getId(), "pendiente", now);
                        return progressRepo.save(mp);
                    });
        }

        return courseProgress;
    }

    // ========================
    // COMPLETAR CURSO
    // ========================
    public Progress completeCourse(Long courseId, Long userId) {
        String now = Instant.now().toString();
        Progress p = new Progress(userId, courseId, "completado", now);
        progressRepo.save(p);

        // crear badge
        Badge b = new Badge(null, userId, courseId, now);
        badgeRepo.save(b);

        return p;
    }

    // ========================
    // COMPLETAR MÓDULO
    // ========================
    public Progress completeModule(Long courseId, Long moduleId, Long userId) {
        String now = Instant.now().toString();

        // buscar progreso del módulo
        Progress moduleProgress = progressRepo.findByUserIdAndCourseIdAndModuleId(userId, courseId, moduleId)
                .orElseThrow(() -> new RuntimeException("Progreso del módulo no encontrado"));

        moduleProgress.setStatus("completado");
        moduleProgress.setUpdatedAt(now);
        progressRepo.save(moduleProgress);

        // recalcular curso
        List<Module> modules = moduleRepo.findByCourseIdOrderByOrderIndex(courseId);
        long total = modules.size();
        long completed = progressRepo.findByUserId(userId).stream()
                .filter(x -> Objects.equals(x.getCourseId(), courseId) &&
                        x.getModuleId() != null &&
                        "completado".equals(x.getStatus()))
                .count();

        // actualizar progreso del curso
        Progress courseProgress = progressRepo.findByUserIdAndCourseIdAndModuleIdIsNull(userId, courseId)
                .orElseThrow(() -> new RuntimeException("Progreso del curso no encontrado"));

        if (completed == total && total > 0) {
            courseProgress.setStatus("completado");
            progressRepo.save(courseProgress);

            // badge
            Badge b = new Badge(null, userId, courseId, now);
            badgeRepo.save(b);
        } else if (completed > 0) {
            courseProgress.setStatus("en progreso");
            progressRepo.save(courseProgress);
        }

        return moduleProgress;
    }

    public Module addModule(Long courseId, Module module) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id " + courseId));
        module.setCourse(course);
        return moduleRepo.save(module);
    }


    // ========================
    // CRUD CURSOS
    // ========================
    public List<Course> listCourses() {
        return courseRepo.findAllByOrderByModuleAscIdAsc();
    }

    public Optional<Course> findById(Long id) {
        return courseRepo.findById(id);
    }

    public Course saveCourse(Course c) {
        return courseRepo.save(c);
    }

    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }

    public List<Module> getModules(Long courseId) {
        return moduleRepo.findByCourseIdOrderByOrderIndex(courseId);
    }
}

