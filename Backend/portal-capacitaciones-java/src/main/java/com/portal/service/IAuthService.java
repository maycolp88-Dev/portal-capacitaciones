package com.portal.service;

import com.portal.model.User;

import java.util.Optional;

/**
 * Servicio que define las operaciones de autenticación de usuarios.
 */
public interface IAuthService {

    /**
     * Valida las credenciales del usuario.
     *
     * @param username nombre de usuario
     * @param password contraseña en texto plano
     * @return un Optional con el usuario autenticado si las credenciales son válidas,
     *         de lo contrario Optional.empty()
     */
    Optional<User> login(String username, String password);
}

