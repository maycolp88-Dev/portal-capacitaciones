package com.portal.service.impl;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock private CourseRepository courseRepo;
    @Mock private ProgressRepository progressRepo;
    @Mock private BadgeRepository badgeRepo;
    @Mock private ModuleRepository moduleRepo;

    @InjectMocks private CourseServiceImpl courseService;

    private Course course;
    private Module module;
    private Progress progress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Course();
        course.setId(1L);
        course.setTitle("Java Basics");
        course.setDescription("Curso de introducción a Java");

        module = new Module();
        module.setId(100L);
        module.setTitle("Intro");
        module.setDescription("Primer módulo");
        module.setOrderIndex(1);
        module.setCourse(course);

        progress = new Progress(1L, 1L, 100L, "pendiente", "2025-01-01T00:00:00Z");
    }

    @Test
    void listCoursesWithModules_ShouldReturnCourseDTOs() {
        when(courseRepo.findAllByOrderByModuleAscIdAsc()).thenReturn(List.of(course));
        when(moduleRepo.findByCourseIdOrderByOrderIndex(1L)).thenReturn(List.of(module));
        when(progressRepo.findByUserIdAndCourseIdAndModuleId(1L, 1L, 100L))
                .thenReturn(Optional.of(progress));

        List<CourseDTO> result = courseService.listCoursesWithModules(1L);

        assertEquals(1, result.size());
        CourseDTO dto = result.get(0);
        assertEquals("Java Basics", dto.getTitle());
        assertEquals(1, dto.getModules().size());
        assertEquals("pendiente", dto.getModules().get(0).getStatus());
    }

    @Test
    void startCourse_ShouldInitializeProgress() {
        when(progressRepo.findByUserIdAndCourseIdAndModuleIdIsNull(1L, 1L))
                .thenReturn(Optional.empty());
        when(progressRepo.save(any(Progress.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(moduleRepo.findByCourseIdOrderByOrderIndex(1L)).thenReturn(List.of(module));

        Progress result = courseService.startCourse(1L, 1L);

        assertEquals("iniciado", result.getStatus());
        verify(progressRepo, atLeastOnce()).save(any(Progress.class));
    }

    @Test
    void completeCourse_ShouldSaveProgressAndBadge() {
        when(progressRepo.save(any(Progress.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(badgeRepo.save(any(Badge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Progress result = courseService.completeCourse(1L, 1L);

        assertEquals("completado", result.getStatus());
        verify(progressRepo).save(any(Progress.class));
        verify(badgeRepo).save(any(Badge.class));
    }

    @Test
    void completeModule_ShouldUpdateProgressAndSetCourseCompleted() {
        Progress moduleProgress = new Progress(1L, 1L, 100L, "pendiente", "2025-01-01T00:00:00Z");
        Progress courseProgress = new Progress(1L, 1L, null, "iniciado", "2025-01-01T00:00:00Z");

        when(progressRepo.findByUserIdAndCourseIdAndModuleId(1L, 1L, 100L)).thenReturn(Optional.of(moduleProgress));
        when(moduleRepo.findByCourseIdOrderByOrderIndex(1L)).thenReturn(List.of(module));
        when(progressRepo.findByUserId(1L)).thenReturn(List.of(new Progress(1L, 1L, 100L, "completado", "2025-01-01T00:00:00Z")));
        when(progressRepo.findByUserIdAndCourseIdAndModuleIdIsNull(1L, 1L)).thenReturn(Optional.of(courseProgress));

        Progress result = courseService.completeModule(1L, 100L, 1L);

        assertEquals("completado", result.getStatus());
        verify(progressRepo, atLeastOnce()).save(any(Progress.class));
        verify(badgeRepo).save(any(Badge.class));
    }

    @Test
    void addModule_ShouldAssignCourseAndSaveModule() {
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(moduleRepo.save(any(Module.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Module result = courseService.addModule(1L, module);

        assertEquals(course, result.getCourse());
        verify(moduleRepo).save(module);
    }

    @Test
    void listCourses_ShouldReturnCourses() {
        when(courseRepo.findAllByOrderByModuleAscIdAsc()).thenReturn(List.of(course));

        List<Course> result = courseService.listCourses();

        assertEquals(1, result.size());
        assertEquals("Java Basics", result.get(0).getTitle());
    }

    @Test
    void findById_ShouldReturnCourse() {
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Java Basics", result.get().getTitle());
    }

    @Test
    void saveCourse_ShouldSaveAndReturnCourse() {
        when(courseRepo.save(any(Course.class))).thenReturn(course);

        Course result = courseService.saveCourse(course);

        assertEquals("Java Basics", result.getTitle());
        verify(courseRepo).save(course);
    }

    @Test
    void deleteCourse_ShouldCallRepoDelete() {
        doNothing().when(courseRepo).deleteById(1L);

        courseService.deleteCourse(1L);

        verify(courseRepo).deleteById(1L);
    }

    @Test
    void getModules_ShouldReturnModules() {
        when(moduleRepo.findByCourseIdOrderByOrderIndex(1L)).thenReturn(List.of(module));

        List<Module> result = courseService.getModules(1L);

        assertEquals(1, result.size());
        assertEquals("Intro", result.get(0).getTitle());
    }
}
