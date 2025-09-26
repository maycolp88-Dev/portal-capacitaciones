package com.portal.service;

import com.portal.model.User;
import com.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository){ this.userRepository = userRepository; }

    public Optional<User> login(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
