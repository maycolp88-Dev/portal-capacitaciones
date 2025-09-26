package com.portal.controller;

import com.portal.DTO.UserDTO;
import com.portal.repository.UserRepository;
import com.portal.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        return userRepository.findByUsernameAndPassword(username, password)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(
                        new UserDTO(u.getId(), u.getUsername(), u.isAdmin())
                ))
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(Map.of("error", "Credenciales inválidas")));
    }
}

