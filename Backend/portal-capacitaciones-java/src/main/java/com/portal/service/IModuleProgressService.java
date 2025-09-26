package com.portal.service;

import com.portal.model.ModuleProgress;

/**
 * Servicio que maneja el progreso de los módulos de un curso.
 */
public interface IModuleProgressService {

    /**
     * Marca un módulo como iniciado para un usuario.
     *
     * @param userId   identificador del usuario
     * @param moduleId identificador del módulo
     * @return progreso del módulo actualizado o recién creado
     */
    ModuleProgress startModule(Long userId, Long moduleId);

    /**
     * Marca un módulo como completado para un usuario y actualiza
     * el progreso del curso si corresponde.
     *
     * @param userId   identificador del usuario
     * @param moduleId identificador del módulo
     * @return progreso del módulo actualizado
     */
    ModuleProgress completeModule(Long userId, Long moduleId);
}
