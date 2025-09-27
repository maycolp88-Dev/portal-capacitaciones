package com.portal.repository;

import com.portal.model.ModuleProgress;
import com.portal.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Long> {
    List<ModuleProgress> findByUserIdAndModuleCourseId(Long userId, Long courseId);
    Optional<ModuleProgress> findByUserIdAndModule(Long userId, Module module);
}

