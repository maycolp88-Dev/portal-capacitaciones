package com.portal.repository;

import com.portal.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByCourseIdOrderByOrderIndex(Long courseId);
    List<Module> findByCourseId(Long courseId);
    long countByCourseId(Long courseId);
}
