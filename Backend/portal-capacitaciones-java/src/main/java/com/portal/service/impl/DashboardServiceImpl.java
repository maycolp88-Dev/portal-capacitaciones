package com.portal.service.impl;

import com.portal.model.Progress;
import com.portal.model.Badge;
import com.portal.model.Course;
import com.portal.repository.ProgressRepository;
import com.portal.repository.BadgeRepository;
import com.portal.repository.CourseRepository;
import com.portal.repository.ModuleRepository;
import com.portal.service.IDashboardService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación de {@link IDashboardService}.
 * Construye estadísticas y resúmenes para el dashboard de usuario.
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

    private final CourseRepository courseRepo;
    private final ProgressRepository progressRepo;
    private final BadgeRepository badgeRepo;
    private final ModuleRepository moduleRepo;

    public DashboardServiceImpl(CourseRepository courseRepo,
                                ProgressRepository progressRepo,
                                BadgeRepository badgeRepo,
                                ModuleRepository moduleRepo) {
        this.courseRepo = courseRepo;
        this.progressRepo = progressRepo;
        this.badgeRepo = badgeRepo;
        this.moduleRepo = moduleRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> buildDashboard(Long userId) {
        Map<String, Object> out = new LinkedHashMap<>();

        List<Course> courses = courseRepo.findAll();
        List<Progress> userProgress = progressRepo.findByUserId(userId);

        long totalCourses = courses.size();

        Set<Long> started = userProgress.stream()
                .map(Progress::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        long coursesStarted = started.size();

        long coursesCompleted = userProgress.stream()
                .filter(p -> p.getModuleId() == null && "completado".equals(p.getStatus()))
                .map(Progress::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .size();

        List<Map<String, Object>> badgesOut = new ArrayList<>();
        List<Badge> badges = badgeRepo.findByUserId(userId);
        for (Badge b : badges) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", b.getId());
            m.put("courseId", b.getCourseId());
            String title = courseRepo.findById(b.getCourseId()).map(Course::getTitle).orElse("Curso desconocido");
            m.put("courseTitle", title);
            m.put("awardedAt", b.getAwardedAt());
            badgesOut.add(m);
        }

        int sumPercent = 0;
        List<Map<String, Object>> courseSummaries = new ArrayList<>();
        for (Course c : courses) {
            long totalModules = moduleRepo.countByCourseId(c.getId());
            long completedModules = userProgress.stream()
                    .filter(p -> Objects.equals(p.getCourseId(), c.getId()) && p.getModuleId() != null && "completado".equals(p.getStatus()))
                    .count();

            int percent = totalModules > 0 ? (int) ((completedModules * 100.0) / totalModules) : 0;
            sumPercent += percent;

            String status = percent == 100 ? "completado" : (percent > 0 ? "en progreso" : "pendiente");

            Map<String, Object> cm = new LinkedHashMap<>();
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
