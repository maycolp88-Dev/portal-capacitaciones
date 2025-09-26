package com.portal.service;

import com.portal.model.Module;
import com.portal.model.ModuleProgress;
import com.portal.repository.ModuleProgressRepository;
import com.portal.repository.ModuleRepository;
import com.portal.repository.CourseRepository;
import com.portal.repository.ProgressRepository;
import com.portal.model.Progress;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ModuleProgressService {

    private final ModuleProgressRepository moduleProgressRepository;
    private final ModuleRepository moduleRepository;
    private final ProgressRepository progressRepository;
    private final CourseRepository courseRepository;

    DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public ModuleProgressService(ModuleProgressRepository moduleProgressRepository,
                                 ModuleRepository moduleRepository,
                                 ProgressRepository progressRepository,
                                 CourseRepository courseRepository) {
        this.moduleProgressRepository = moduleProgressRepository;
        this.moduleRepository = moduleRepository;
        this.progressRepository = progressRepository;
        this.courseRepository = courseRepository;
    }

    public ModuleProgress startModule(Long userId, Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
        return moduleProgressRepository.findByUserIdAndModule(userId, module)
                .orElseGet(() -> {
                    ModuleProgress mp = new ModuleProgress();
                    mp.setUserId(userId);
                    mp.setModule(module);
                    mp.setStatus("iniciado");
                    mp.setUpdatedAt(LocalDateTime.now());
                    return moduleProgressRepository.save(mp);
                });
    }

    public ModuleProgress completeModule(Long userId, Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
        ModuleProgress mp = moduleProgressRepository.findByUserIdAndModule(userId, module)
                .orElseThrow(() -> new RuntimeException("Módulo no iniciado"));

        mp.setStatus("completado");
        mp.setUpdatedAt(LocalDateTime.now());
        moduleProgressRepository.save(mp);

        List<ModuleProgress> progresses = moduleProgressRepository.findByUserIdAndModuleCourseId(userId, module.getCourse().getId());
        boolean allCompleted = progresses.size() == moduleRepository.countByCourseId(module.getCourse().getId())
                && progresses.stream().allMatch(p -> "completado".equals(p.getStatus()));

        if (allCompleted) {
            Progress courseProgress = progressRepository
                    .findByUserIdAndCourseIdAndModuleIdIsNull(userId, module.getCourse().getId())
                    .orElseThrow(() -> new RuntimeException("Progreso de curso no encontrado"));

            courseProgress.setStatus("completado");
            courseProgress.setUpdatedAt(LocalDateTime.now().format(fmt));
            progressRepository.save(courseProgress);
        }

        return mp;
    }
}

