-- data.sql (principal)
-- Usuarios
INSERT INTO users (username, password, is_admin) VALUES ('alice','password123', false);
INSERT INTO users (username, password, is_admin) VALUES ('bob','password123', false);
INSERT INTO users (username, password, is_admin) VALUES ('admin','admin123', true);

-- Cursos
INSERT INTO courses (title, module, description) VALUES ('Intro Fullstack', 'Fullstack', 'Fundamentos de Fullstack');
INSERT INTO courses (title, module, description) VALUES ('APIs con Java', 'APIs e Integraciones', 'Crear APIs REST con Spring Boot');
INSERT INTO courses (title, module, description) VALUES ('Fundamentos Cloud', 'Cloud', 'Conceptos básicos de Cloud');
INSERT INTO courses (title, module, description) VALUES ('Data Basics', 'Data Engineer', 'Introducción a Data Engineering');
INSERT INTO courses (title, module, description) VALUES ('Spring Boot Intro', 'Fullstack', 'Introducción a Spring Boot');

-- Módulos por curso
-- Intro Fullstack (course_id = 1)
INSERT INTO module (title, description, order_index, course_id) VALUES
('HTML & CSS', 'Fundamentos de frontend', 1, 1),
('JavaScript', 'Bases de JS y DOM', 2, 1),
('Spring Boot Intro', 'Intro al backend con Spring Boot', 3, 1),
('APIs REST', 'Creación de servicios REST con Spring', 4, 1),
('Deploy', 'Publicación de apps en la nube', 5, 1);

-- APIs con Java (course_id = 2)
INSERT INTO module (title, description, order_index, course_id) VALUES
('Intro a REST', 'Conceptos y fundamentos de REST', 1, 2),
('Spring Boot Controllers', 'Definición de endpoints', 2, 2),
('Persistencia con JPA', 'Acceso a datos con JPA/Hibernate', 3, 2),
('Documentación con Swagger', 'Generación de docs con OpenAPI', 4, 2),
('Seguridad básica', 'Autenticación y autorización', 5, 2);

-- Fundamentos Cloud (course_id = 3)
INSERT INTO module (title, description, order_index, course_id) VALUES
('Conceptos de Cloud', 'Modelo IaaS, PaaS y SaaS', 1, 3),
('AWS Basics', 'Servicios principales de AWS', 2, 3),
('Azure Basics', 'Servicios principales de Azure', 3, 3),
('Google Cloud Basics', 'Servicios principales de GCP', 4, 3),
('DevOps & CI/CD', 'Integración y despliegue continuo en Cloud', 5, 3);

-- Data Basics (course_id = 4)
INSERT INTO module (title, description, order_index, course_id) VALUES
('Introducción a Data', 'Conceptos básicos de datos', 1, 4),
('SQL Fundamentals', 'Consultas básicas en SQL', 2, 4),
('ETL Basics', 'Extracción, Transformación y Carga de datos', 3, 4),
('Big Data Concepts', 'Hadoop, Spark y ecosistema Big Data', 4, 4),
('Data Visualization', 'Visualización con PowerBI/Tableau', 5, 4);

-- Spring Boot Intro (course_id = 5)
INSERT INTO module (title, description, order_index, course_id) VALUES
('Spring Boot Setup', 'Configuración inicial de un proyecto', 1, 5),
('Controllers', 'Creación de controladores', 2, 5),
('Services', 'Definición de la capa de servicios', 3, 5),
('Persistencia con JPA', 'Acceso a base de datos con Spring Data', 4, 5),
('Testing con JUnit', 'Pruebas unitarias en Spring Boot', 5, 5);
