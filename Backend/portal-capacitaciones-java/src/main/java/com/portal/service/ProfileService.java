package com.portal.service;

import com.portal.model.Course;
import com.portal.model.Module;
import com.portal.model.Progress;
import com.portal.model.User;
import com.portal.repository.BadgeRepository;
import com.portal.repository.CourseRepository;
import com.portal.repository.ModuleRepository;
import com.portal.repository.ProgressRepository;
import com.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final ModuleRepository moduleRepo;
    private final ProgressRepository progressRepo;
    private final BadgeRepository badgeRepo;

    public ProfileService(UserRepository userRepo,
                          CourseRepository courseRepo,
                          ModuleRepository moduleRepo,
                          ProgressRepository progressRepo,
                          BadgeRepository badgeRepo) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.moduleRepo = moduleRepo;
        this.progressRepo = progressRepo;
        this.badgeRepo = badgeRepo;
    }

    public Map<String,Object> getProfile(Long userId) {
        Map<String,Object> out = new LinkedHashMap<>();

        // usuario
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        out.put("user", Map.of(
                "id", u.getId(),
                "username", u.getUsername(),
                "admin", u.isAdmin()
        ));

        // recuperar progresos del usuario
        List<Progress> progresses = progressRepo.findByUserId(userId);

        // agrupar por curso
        Map<Long, List<Progress>> byCourse = progresses.stream()
                .collect(Collectors.groupingBy(Progress::getCourseId));

        List<Map<String,Object>> progressOut = new ArrayList<>();

        for (var entry : byCourse.entrySet()) {
            Long courseId = entry.getKey();
            Course c = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

            List<Module> modules = moduleRepo.findByCourseIdOrderByOrderIndex(courseId);
            List<Progress> courseProgress = entry.getValue();

            long completed = 0;
            List<Map<String,Object>> moduleDtos = new ArrayList<>();
            for (Module m : modules) {
                String status = courseProgress.stream()
                        .filter(p -> Objects.equals(p.getModuleId(), m.getId()))
                        .map(Progress::getStatus)
                        .findFirst()
                        .orElse("pendiente");

                if ("completado".equals(status)) completed++;

                moduleDtos.add(Map.of(
                        "id", m.getId(),
                        "title", m.getTitle(),
                        "status", status
                ));
            }

            int percent = modules.isEmpty() ? 0 : (int)((completed * 100.0) / modules.size());
            String courseStatus = percent == 100 ? "completado" :
                    (percent > 0 ? "en progreso" : "iniciado");

            progressOut.add(Map.of(
                    "courseId", c.getId(),
                    "title", c.getTitle(),
                    "status", courseStatus,
                    "progress", percent,
                    "modules", moduleDtos
            ));
        }

        out.put("progress", progressOut);

        // insignias
        List<Map<String,Object>> badgesOut = badgeRepo.findByUserId(userId).stream()
                .map(b -> {
                    Map<String,Object> m = new HashMap<>();
                    m.put("id", b.getId());
                    m.put("courseId", b.getCourseId());
                    m.put("awardedAt", b.getAwardedAt());

                    courseRepo.findById(b.getCourseId())
                            .ifPresent(course -> m.put("courseTitle", course.getTitle()));

                    return m;
                })
                .collect(Collectors.toList());
        out.put("badges", badgesOut);

        return out;
    }
}

