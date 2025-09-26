package com.portal.service;

import com.portal.model.User;

import java.util.Optional;

/**
 * Servicio para la gestión de usuarios.
 */
public interface IUserService {

    /**
     * Busca un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return un {@link Optional} con el usuario si existe
     */
    Optional<User> findById(Integer id);
}

