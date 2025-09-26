package com.portal.service.impl;

import com.portal.model.Badge;
import com.portal.model.Course;
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

class DashboardServiceImplTest {

    @Mock private CourseRepository courseRepo;
    @Mock private ProgressRepository progressRepo;
    @Mock private BadgeRepository badgeRepo;
    @Mock private ModuleRepository moduleRepo;

    @InjectMocks private DashboardServiceImpl dashboardService;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Java Basics");

        course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Spring Boot");
    }

    @Test
    void buildDashboard_ShouldReturnEmptyStats_WhenNoCourses() {
        when(courseRepo.findAll()).thenReturn(Collections.emptyList());
        when(progressRepo.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(badgeRepo.findByUserId(1L)).thenReturn(Collections.emptyList());

        Map<String, Object> result = dashboardService.buildDashboard(1L);

        assertEquals(0L, result.get("totalCourses"));
        assertEquals(0L, result.get("coursesStarted"));
        assertEquals(0L, result.get("coursesCompleted"));
        assertEquals(0, result.get("avgProgress"));
        assertTrue(((List<?>) result.get("badges")).isEmpty());
        assertTrue(((List<?>) result.get("courses")).isEmpty());
    }

    @Test
    void buildDashboard_ShouldIncludeBadges() {
        Badge badge = new Badge(1L, 1L, 1L, "2025-01-01T00:00:00Z");

        when(courseRepo.findAll()).thenReturn(List.of(course1));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course1));
        when(progressRepo.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(badgeRepo.findByUserId(1L)).thenReturn(List.of(badge));
        when(moduleRepo.countByCourseId(1L)).thenReturn(0L);

        Map<String, Object> result = dashboardService.buildDashboard(1L);

        List<Map<String, Object>> badges = (List<Map<String, Object>>) result.get("badges");

        assertEquals(1, badges.size());
        Map<String, Object> b = badges.get(0);
        assertEquals(1L, b.get("id"));
        assertEquals("Java Basics", b.get("courseTitle"));
        assertEquals("2025-01-01T00:00:00Z", b.get("awardedAt"));
    }
}
