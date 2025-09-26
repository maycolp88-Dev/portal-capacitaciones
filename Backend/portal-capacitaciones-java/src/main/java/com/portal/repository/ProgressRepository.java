package com.portal.repository;

import com.portal.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByUserIdAndCourseIdAndModuleIdIsNull(Long userId, Long courseId);
    Optional<Progress> findByUserIdAndCourseIdAndModuleId(Long userId, Long courseId, Long moduleId);
    long countByUserIdAndCourseIdAndStatus(Long userId, Long courseId, String status);
    List<Progress> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Progress> findByUserId(Long userId);
}





