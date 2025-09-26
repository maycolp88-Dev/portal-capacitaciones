package com.portal.controller;

import com.portal.model.Badge;
import com.portal.model.Progress;
import com.portal.model.User;
import com.portal.repository.BadgeRepository;
import com.portal.repository.ProgressRepository;
import com.portal.repository.UserRepository;
import com.portal.service.IProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private IProfileService profileService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProgressRepository progressRepository;

    @MockBean
    private BadgeRepository badgeRepository;

    @Test
    void profileSuccess() throws Exception {
        User u = new User();
        u.setId(1L);
        u.setUsername("testuser");
        u.setAdmin(false);

        Map<String,Object> profile = new HashMap<>();
        profile.put("user", Map.of("id", 1L, "username", "testuser", "admin", false));

        when(profileService.getProfile(1L)).thenReturn(profile);

        mockMvc.perform(get("/api/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("testuser")); // ahora pasa ✅
    }
}

