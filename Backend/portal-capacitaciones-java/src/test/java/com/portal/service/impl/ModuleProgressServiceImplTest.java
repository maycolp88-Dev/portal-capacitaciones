package com.portal.service.impl;

import com.portal.model.Module;
import com.portal.model.ModuleProgress;
import com.portal.model.Progress;
import com.portal.repository.CourseRepository;
import com.portal.repository.ModuleProgressRepository;
import com.portal.repository.ModuleRepository;
import com.portal.repository.ProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModuleProgressServiceImplTest {

    @Mock private ModuleProgressRepository moduleProgressRepository;
    @Mock private ModuleRepository moduleRepository;
    @Mock private ProgressRepository progressRepository;
    @Mock private CourseRepository courseRepository;

    @InjectMocks private ModuleProgressServiceImpl service;

    private Module module;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        module = new Module();
        module.setId(10L);
        module.setTitle("Spring Boot Controllers");

        var course = new com.portal.model.Course();
        course.setId(1L);
        course.setTitle("Java APIs");
        module.setCourse(course);
    }

    @Test
    void startModule_ShouldCreateNewProgress_WhenNotExists() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));
        when(moduleProgressRepository.findByUserIdAndModule(1L, module))
                .thenReturn(Optional.empty());

        ModuleProgress saved = new ModuleProgress();
        saved.setId(100L);
        saved.setUserId(1L);
        saved.setModule(module);
        saved.setStatus("iniciado");
        saved.setUpdatedAt(LocalDateTime.now());

        when(moduleProgressRepository.save(any(ModuleProgress.class))).thenReturn(saved);

        ModuleProgress result = service.startModule(1L, 10L);

        assertNotNull(result);
        assertEquals("iniciado", result.getStatus());
        assertEquals(1L, result.getUserId());
        assertEquals(module, result.getModule());

        verify(moduleProgressRepository).save(any(ModuleProgress.class));
    }

    @Test
    void startModule_ShouldReturnExistingProgress_WhenAlreadyStarted() {
        ModuleProgress existing = new ModuleProgress();
        existing.setId(200L);
        existing.setUserId(1L);
        existing.setModule(module);
        existing.setStatus("iniciado");

        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));
        when(moduleProgressRepository.findByUserIdAndModule(1L, module))
                .thenReturn(Optional.of(existing));

        ModuleProgress result = service.startModule(1L, 10L);

        assertEquals(existing, result);
        verify(moduleProgressRepository, never()).save(any());
    }

    @Test
    void completeModule_ShouldMarkAsCompleted() {
        ModuleProgress mp = new ModuleProgress();
        mp.setId(300L);
        mp.setUserId(1L);
        mp.setModule(module);
        mp.setStatus("iniciado");

        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));
        when(moduleProgressRepository.findByUserIdAndModule(1L, module)).thenReturn(Optional.of(mp));
        when(moduleProgressRepository.findByUserIdAndModuleCourseId(1L, 1L)).thenReturn(List.of(mp));
        when(moduleRepository.countByCourseId(1L)).thenReturn(1L);

        Progress courseProgress = new Progress(1L, 1L, null, "en progreso", "2025-01-01T00:00:00Z");
        when(progressRepository.findByUserIdAndCourseIdAndModuleIdIsNull(1L, 1L)).thenReturn(Optional.of(courseProgress));

        ModuleProgress result = service.completeModule(1L, 10L);

        assertEquals("completado", result.getStatus());
        verify(moduleProgressRepository).save(mp);
        verify(progressRepository).save(courseProgress);
        assertEquals("completado", courseProgress.getStatus());
    }

    @Test
    void completeModule_ShouldThrowException_WhenModuleNotStarted() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));
        when(moduleProgressRepository.findByUserIdAndModule(1L, module)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                service.completeModule(1L, 10L));

        assertEquals("Módulo no iniciado", ex.getMessage());
    }

    @Test
    void startModule_ShouldThrowException_WhenModuleNotFound() {
        when(moduleRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                service.startModule(1L, 99L));

        assertEquals("Módulo no encontrado", ex.getMessage());
    }
}
