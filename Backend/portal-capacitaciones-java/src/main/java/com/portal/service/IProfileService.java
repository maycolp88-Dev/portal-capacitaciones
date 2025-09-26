package com.portal.service;

import java.util.Map;

/**
 * Servicio para la construcción del perfil del usuario,
 * incluyendo datos personales, progreso en cursos y
 * las insignias obtenidas.
 */
public interface IProfileService {

    /**
     * Obtiene el perfil completo de un usuario.
     *
     * @param userId identificador del usuario
     * @return mapa con información del usuario, progreso y badges
     */
    Map<String, Object> getProfile(Long userId);
}


