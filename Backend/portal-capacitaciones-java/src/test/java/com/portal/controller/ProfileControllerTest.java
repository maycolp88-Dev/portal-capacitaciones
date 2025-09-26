package com.portal.controller;

import com.portal.model.Badge;
import com.portal.model.Progress;
import com.portal.model.User;
import com.portal.repository.BadgeRepository;
import com.portal.repository.ProgressRepository;
import com.portal.repository.UserRepository;
import com.portal.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProgressRepository progressRepository;

    @MockBean
    private BadgeRepository badgeRepository;

    @Test
    void profileSuccess() throws Exception {
        User u = new User(1L, "alice", "password123", false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(progressRepository.findByUserId(1L)).thenReturn(
                List.of(new Progress(1L, 2L, "completado", "2025-09-24T10:00:00Z"))
        );
        when(badgeRepository.findByUserId(1L)).thenReturn(
                List.of(new Badge(1L, 1L, 2L, "2025-09-24T10:00:00Z"))
        );

        mockMvc.perform(get("/api/profile/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("alice"))
                .andExpect(jsonPath("$.progress[0].status").value("completado"))
                .andExpect(jsonPath("$.badges[0].courseId").value(2));
    }

    @Test
    void profileNotFound() throws Exception {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/profile/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Usuario no encontrado"));
    }
}

