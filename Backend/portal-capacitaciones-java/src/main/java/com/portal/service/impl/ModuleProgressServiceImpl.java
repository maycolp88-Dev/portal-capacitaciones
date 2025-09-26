package com.portal.service.impl;

import com.portal.model.Module;
import com.portal.model.ModuleProgress;
import com.portal.model.Progress;
import com.portal.repository.ModuleProgressRepository;
import com.portal.repository.ModuleRepository;
import com.portal.repository.ProgressRepository;
import com.portal.repository.CourseRepository;
import com.portal.service.IModuleProgressService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Implementación de {@link IModuleProgressService}.
 * Maneja el inicio y la finalización de módulos, así como la
 * actualización del progreso del curso.
 */
@Service
public class ModuleProgressServiceImpl implements IModuleProgressService {

    private final ModuleProgressRepository moduleProgressRepository;
    private final ModuleRepository moduleRepository;
    private final ProgressRepository progressRepository;
    private final CourseRepository courseRepository;

    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ModuleProgressServiceImpl(ModuleProgressRepository moduleProgressRepository,
                                     ModuleRepository moduleRepository,
                                     ProgressRepository progressRepository,
                                     CourseRepository courseRepository) {
        this.moduleProgressRepository = moduleProgressRepository;
        this.moduleRepository = moduleRepository;
        this.progressRepository = progressRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ModuleProgress completeModule(Long userId, Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        ModuleProgress mp = moduleProgressRepository.findByUserIdAndModule(userId, module)
                .orElseThrow(() -> new RuntimeException("Módulo no iniciado"));

        // Marcar módulo como completado
        mp.setStatus("completado");
        mp.setUpdatedAt(LocalDateTime.now());
        moduleProgressRepository.save(mp);

        // Validar si todos los módulos del curso están completados
        List<ModuleProgress> progresses =
                moduleProgressRepository.findByUserIdAndModuleCourseId(userId, module.getCourse().getId());

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

