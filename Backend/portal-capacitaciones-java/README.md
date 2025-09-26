# Portal - Backend Java (Spring Boot)

Proyecto generado para la Kata: Portal de Capacitaciones Interactivo.

## Requisitos
- Java 17+
- Maven 3.8+

## Ejecutar local
1. Compilar y ejecutar:
   ```bash
   mvn spring-boot:run
   ```
   El backend quedará en http://localhost:8090

2. Endpoints principales:
   - POST /api/login  {username, password}
   - GET  /api/courses
   - POST /api/courses/{id}/start {userId}
   - POST /api/courses/{id}/complete {userId}
   - GET  /api/profile/{userId}

## Base de datos
Usamos H2 en archivo en `./data/portal-db`. Los datos iniciales se cargan desde `src/main/resources/data.sql`.

## Tests
Ejecutar:
```bash
mvn test
```

## Docker (opcional)
Puedes crear un Dockerfile y docker-compose para integrar con el frontend. Si quieres, lo genero también.
