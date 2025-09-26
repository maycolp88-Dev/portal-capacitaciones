package com.portal.service;

import com.portal.model.Progress;
import com.portal.model.Badge;
import com.portal.model.Course;
import com.portal.repository.ProgressRepository;
import com.portal.repository.BadgeRepository;
import com.portal.repository.CourseRepository;
import com.portal.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final CourseRepository courseRepo;
    private final ProgressRepository progressRepo;
    private final BadgeRepository badgeRepo;
    private final ModuleRepository moduleRepo;

    public DashboardService(CourseRepository courseRepo,
                            ProgressRepository progressRepo,
                            BadgeRepository badgeRepo,
                            ModuleRepository moduleRepo) {
        this.courseRepo = courseRepo;
        this.progressRepo = progressRepo;
        this.badgeRepo = badgeRepo;
        this.moduleRepo = moduleRepo;
    }

    public Map<String, Object> buildDashboard(Long userId) {
        Map<String, Object> out = new LinkedHashMap<>();

        List<Course> courses = courseRepo.findAll();
        List<Progress> userProgress = progressRepo.findByUserId(userId); // Asegúrate que existe este método

        long totalCourses = courses.size();

        // coursesStarted = número de courseId distintos en userProgress
        Set<Long> started = userProgress.stream()
                .map(Progress::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        long coursesStarted = started.size();

        // coursesCompleted = courses que tienen progreso de curso (moduleId == null) con status == "completado"
        long coursesCompleted = userProgress.stream()
                .filter(p -> p.getModuleId() == null && "completado".equals(p.getStatus()))
                .map(Progress::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .size();

        // badges: mapear badges a List<Map<String,Object>>
        List<Map<String,Object>> badgesOut = new ArrayList<>();
        List<Badge> badges = badgeRepo.findByUserId(userId);
        for (Badge b : badges) {
            Map<String,Object> m = new LinkedHashMap<>();
            m.put("id", b.getId());
            m.put("courseId", b.getCourseId());
            String title = courseRepo.findById(b.getCourseId()).map(Course::getTitle).orElse("Curso desconocido");
            m.put("courseTitle", title);
            m.put("awardedAt", b.getAwardedAt());
            badgesOut.add(m);
        }

        // resumen de cursos (progress por curso)
        int sumPercent = 0;
        List<Map<String,Object>> courseSummaries = new ArrayList<>();
        for (Course c : courses) {
            long totalModules = moduleRepo.countByCourseId(c.getId()); // asegúrate que existe countByCourseId
            long completedModules = userProgress.stream()
                    .filter(p -> Objects.equals(p.getCourseId(), c.getId()) && p.getModuleId() != null && "completado".equals(p.getStatus()))
                    .count();

            int percent = totalModules > 0 ? (int)((completedModules * 100.0) / totalModules) : 0;
            sumPercent += percent;

            String status = percent == 100 ? "completado" : (percent > 0 ? "en progreso" : "pendiente");

            Map<String,Object> cm = new LinkedHashMap<>();
            cm.put("id", c.getId());
            cm.put("title", c.getTitle());
            cm.put("progress", percent);
            cm.put("status", status);
            courseSummaries.add(cm);
        }

        int avgProgress = courses.isEmpty() ? 0 : (sumPercent / courses.size());

        out.put("totalCourses", totalCourses);
        out.put("coursesStarted", coursesStarted);
        out.put("coursesCompleted", coursesCompleted);
        out.put("badges", badgesOut);
        out.put("avgProgress", avgProgress);
        out.put("courses", courseSummaries);

        return out;
    }
}

