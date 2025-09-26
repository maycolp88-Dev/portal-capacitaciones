package com.portal.controller;

import com.portal.DTO.CourseDTO;
import com.portal.model.Course;
import com.portal.repository.ModuleRepository;
import com.portal.repository.ProgressRepository;
import com.portal.service.ICourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICourseService courseService;

    @MockBean
    private ModuleRepository moduleRepo;

    @MockBean
    private ProgressRepository progressRepo;

    @Test
    void listCourses() throws Exception {
        List<CourseDTO> courses = List.of(
                new CourseDTO(1L, "Curso 1", "Fullstack", "Intro a Fullstack", 0, List.of(), "iniciado"),
                new CourseDTO(2L, "Curso 2", "Cloud", "Intro a Cloud", 0, List.of(), "iniciado")
        );

        when(courseService.listCoursesWithModules(0L)).thenReturn(courses);

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getCourseById() throws Exception {
        Course c = new Course(1L, "Curso 1", "Fullstack", "Intro a Fullstack");

        when(courseService.findById(1L)).thenReturn(Optional.of(c));

        mockMvc.perform(get("/api/courses/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Curso 1"));
    }
}
