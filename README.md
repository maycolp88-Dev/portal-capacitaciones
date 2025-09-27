# 📚 Portal de Capacitaciones

Portal de Capacitaciones es una aplicación **Fullstack** que permite gestionar cursos, módulos y el progreso de los usuarios dentro de un entorno de aprendizaje digital.

---

## 🚀 Funcionalidades principales

- **Usuarios**
  - Registro y login de usuarios.
  - Distinción entre usuario **Administrador** y usuario **Alumno**.

- **Administradores**
  - Crear, listar y eliminar cursos.
  - Agregar y eliminar módulos dentro de un curso.
  - Visualizar el contenido de cada curso/módulo.

- **Alumnos**
  - Listar cursos disponibles.
  - Iniciar cursos y marcar módulos como completados.
  - Visualizar progreso por curso (% de avance).
  - Obtener insignias (**badges**) al completar un curso.

- **Dashboard / Perfil**
  - Resumen del progreso general.
  - Promedio de avance en cursos.
  - Cursos iniciados y completados.
  - Insignias obtenidas.

---

## 🏗️ Estructura del Proyecto

El proyecto sigue una arquitectura monorepo, separando el frontend y el backend para facilitar el desarrollo.

```bash
portal-capacitaciones/
├── backend/          # Directorio principal de la API REST (Spring Boot)
│   ├── src/          # Código fuente de la aplicación (Java)
│   ├── build/        # Directorio de artefactos (p.ej., .jar)
│   └── pom.xml       # Archivo de configuración de dependencias y build (Maven)
|
├── frontend/         # Directorio principal de la Interfaz de Usuario (Angular)
│   ├── src/          # Código fuente de la aplicación (TypeScript)
│   ├── node_modules/ # Dependencias de Node.js
│   └── package.json  # Archivo de configuración de dependencias y scripts
|
└── README.md         # Documentación principal del proyecto
```

---

## ⚙️ Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- H2
- Maven

### Frontend
- Angular 20
- Angular Material
- TypeScript
- RxJS

---
