package com.portal.service;

import com.portal.DTO.CourseDTO;
import com.portal.model.Course;
import com.portal.model.Module;
import com.portal.model.Progress;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que define las operaciones para gestionar cursos, módulos,
 * progreso de usuarios y asignación de insignias (badges).
 */
public interface ICourseService {

    /**
     * Listar cursos con sus módulos y progreso del usuario.
     * @param userId identificador del usuario
     * @return lista de cursos en formato DTO
     */
    List<CourseDTO> listCoursesWithModules(Long userId);

    /**
     * Inicia un curso para un usuario y genera progreso base.
     * @param courseId identificador del curso
     * @param userId identificador del usuario
     * @return progreso inicial del curso
     */
    Progress startCourse(Long courseId, Long userId);

    /**
     * Marca un curso como completado y asigna una insignia.
     * @param courseId identificador del curso
     * @param userId identificador del usuario
     * @return progreso del curso actualizado
     */
    Progress completeCourse(Long courseId, Long userId);

    /**
     * Marca un módulo como completado y actualiza el estado del curso.
     * @param courseId identificador del curso
     * @param moduleId identificador del módulo
     * @param userId identificador del usuario
     * @return progreso actualizado del módulo
     */
    Progress completeModule(Long courseId, Long moduleId, Long userId);

    /**
     * Agrega un módulo a un curso existente.
     * @param courseId identificador del curso
     * @param module módulo a registrar
     * @return módulo persistido
     */
    Module addModule(Long courseId, Module module);

    /**
     * Lista todos los cursos ordenados por categoría y id.
     * @return lista de cursos
     */
    List<Course> listCourses();

    /**
     * Busca un curso por su id.
     * @param id identificador del curso
     * @return curso si existe
     */
    Optional<Course> findById(Long id);

    /**
     * Guarda o actualiza un curso.
     * @param c curso a guardar
     * @return curso persistido
     */
    Course saveCourse(Course c);

    /**
     * Elimina un curso por su id.
     * @param id identificador del curso
     */
    void deleteCourse(Long id);

    /**
     * Obtiene los módulos de un curso.
     * @param courseId identificador del curso
     * @return lista de módulos
     */
    List<Module> getModules(Long courseId);
}
