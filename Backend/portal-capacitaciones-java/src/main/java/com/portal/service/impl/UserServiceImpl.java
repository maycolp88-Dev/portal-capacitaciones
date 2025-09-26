package com.portal.service.impl;

import com.portal.model.User;
import com.portal.repository.UserRepository;
import com.portal.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación de {@link IUserService}.
 * Permite consultar información de usuarios en base de datos.
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(Long.valueOf(id));
    }
}
