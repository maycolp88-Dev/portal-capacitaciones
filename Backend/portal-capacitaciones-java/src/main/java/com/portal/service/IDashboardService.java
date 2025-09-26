package com.portal.service;

import java.util.Map;

/**
 * Servicio que define las operaciones relacionadas con el
 * dashboard del usuario.
 */
public interface IDashboardService {

    /**
     * Construye el tablero de control (dashboard) con estadísticas del usuario.
     *
     * @param userId identificador del usuario
     * @return un mapa con información consolidada del dashboard:
     *         <ul>
     *             <li>totalCourses → número total de cursos</li>
     *             <li>coursesStarted → número de cursos iniciados</li>
     *             <li>coursesCompleted → número de cursos completados</li>
     *             <li>badges → lista de insignias obtenidas</li>
     *             <li>avgProgress → progreso promedio en porcentaje</li>
     *             <li>courses → lista con el resumen de cada curso</li>
     *         </ul>
     */
    Map<String, Object> buildDashboard(Long userId);
}
