package com.portal.service.impl;

import com.portal.model.User;
import com.portal.repository.UserRepository;
import com.portal.service.IAuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación de {@link IAuthService}.
 * Maneja la autenticación de usuarios contra la base de datos.
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
