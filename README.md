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

## 🗂️ Estructura del proyecto

portal-capacitaciones/
│
├── backend/ # Aplicación Spring Boot (API REST + persistencia en base de datos)
│ ├── src/...
│ └── pom.xml
│
├── frontend/ # Aplicación Angular (interfaz de usuario)
│ ├── src/...
│ └── package.json
│
└── README.md


---

## ⚙️ Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- H2 / PostgreSQL
- Maven

### Frontend
- Angular 20
- Angular Material
- TypeScript
- RxJS

---
