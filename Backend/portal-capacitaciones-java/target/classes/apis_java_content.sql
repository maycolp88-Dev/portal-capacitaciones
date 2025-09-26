-- ================================
-- Curso: APIs con Java
-- ================================

-- Módulo 1: Intro a REST
UPDATE module SET content = '
<h2>¿Qué es REST?</h2>
<p>REST (Representational State Transfer) es un estilo arquitectónico para diseñar servicios web.
No es un protocolo ni un estándar, sino un conjunto de principios que utilizan HTTP para exponer recursos.</p>

<h3>Principios Clave</h3>
<ul>
  <li><b>Recursos:</b> Todo se representa como un recurso (usuarios, pedidos, productos).</li>
  <li><b>Identificación:</b> Cada recurso tiene una URI única (ej. <code>/api/users/1</code>).</li>
  <li><b>Stateless:</b> Cada petición es independiente; el servidor no guarda estado de cliente.</li>
  <li><b>Operaciones:</b> Se basan en los métodos HTTP: GET, POST, PUT, DELETE, PATCH.</li>
  <li><b>Representaciones:</b> Los recursos pueden representarse en JSON, XML, etc.</li>
</ul>

<h3>Ejemplo Básico</h3>
<pre><code>GET /api/users        → lista todos los usuarios
GET /api/users/1      → obtiene usuario con id=1
POST /api/users       → crea un nuevo usuario
PUT /api/users/1      → actualiza usuario con id=1
DELETE /api/users/1   → elimina usuario con id=1
</code></pre>

<h3>Buenas Prácticas</h3>
<ul>
  <li>Usar <b>nombres de recursos</b> en plural (<code>/api/products</code>).</li>
  <li>No incluir verbos en la URL (no usar <code>/getUsers</code>).</li>
  <li>Versionar APIs: <code>/api/v1/users</code>.</li>
  <li>Usar códigos de estado HTTP correctos (200 OK, 201 Created, 404 Not Found, 500 Error).</li>
</ul>

<h3>Ejercicio</h3>
<p>Diseña una API REST para gestionar un blog con los siguientes recursos:</p>
<ul>
  <li>Usuarios</li>
  <li>Posts</li>
  <li>Comentarios</li>
</ul>
<p>Crea un listado de endpoints siguiendo las buenas prácticas.</p>
' WHERE title = 'Intro a REST';

-- Módulo 2: Spring Boot Controllers
UPDATE module SET content = '
<h2>Spring Boot Controllers</h2>
<p>En Spring Boot, los controladores son clases que exponen endpoints HTTP usando anotaciones.</p>

<h3>Anotaciones Principales</h3>
<ul>
  <li><code>@RestController:</code> indica que la clase devuelve datos JSON/XML.</li>
  <li><code>@RequestMapping:</code> define la URL base de un controlador.</li>
  <li><code>@GetMapping, @PostMapping, @PutMapping, @DeleteMapping:</code> definen métodos específicos.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>@RestController
@RequestMapping("/api/products")
public class ProductController {

  @GetMapping
  public List&lt;Product&gt; list() {
    return productService.findAll();
  }

  @PostMapping
  public Product create(@RequestBody Product p) {
    return productService.save(p);
  }
}
</code></pre>

<h3>Buenas Prácticas</h3>
<ul>
  <li>Separar lógica en servicios (<code>@Service</code>), no sobrecargar los controladores.</li>
  <li>Devolver códigos HTTP adecuados (<code>ResponseEntity</code>).</li>
  <li>Usar DTOs en vez de exponer directamente las entidades.</li>
</ul>

<h3>Ejercicio</h3>
<p>Implementa un controlador <code>UserController</code> con los métodos:</p>
<ul>
  <li><code>GET /api/users</code> → lista usuarios</li>
  <li><code>POST /api/users</code> → crea un nuevo usuario</li>
  <li><code>GET /api/users/{id}</code> → obtiene un usuario</li>
</ul>
' WHERE title = 'Spring Boot Controllers';

-- Módulo 3: Persistencia con JPA
UPDATE module SET content = '
<h2>Persistencia con JPA</h2>
<p>JPA (Java Persistence API) permite mapear objetos Java a tablas de base de datos.</p>

<h3>Conceptos Clave</h3>
<ul>
  <li><code>@Entity:</code> convierte una clase en una tabla.</li>
  <li><code>@Id</code> y <code>@GeneratedValue:</code> identificador único.</li>
  <li><code>@OneToMany</code>, <code>@ManyToOne</code> → relaciones.</li>
  <li><code>JpaRepository:</code> interfaz de Spring Data JPA para consultas CRUD automáticas.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>@Entity
public class User {
  @Id @GeneratedValue
  private Long id;
  private String username;
  private String email;
}
</code></pre>

<h3>Repositorio</h3>
<pre><code>public interface UserRepository extends JpaRepository&lt;User, Long&gt; {}
</code></pre>

<h3>Ejercicio</h3>
<p>Implementa una entidad <code>Course</code> con atributos: <b>id, title, description</b>.
Crea un <code>CourseRepository</code> que extienda de JpaRepository y prueba un endpoint para listar cursos.</p>
' WHERE title = 'Persistencia con JPA';

-- Módulo 4: Documentación con Swagger
UPDATE module SET content = '
<h2>Documentación con Swagger</h2>
<p>Swagger (OpenAPI) permite documentar APIs y probarlas desde un navegador.</p>

<h3>Dependencia</h3>
<pre><code>implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0"
</code></pre>

<h3>Características</h3>
<ul>
  <li>Genera documentación automática basada en anotaciones.</li>
  <li>Interfaz gráfica en <code>/swagger-ui.html</code>.</li>
  <li>Permite probar peticiones directamente.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>@Operation(summary = "Obtiene todos los usuarios")
@GetMapping("/api/users")
public List&lt;User&gt; listUsers() {
  return userService.findAll();
}
</code></pre>

<h3>Buenas Prácticas</h3>
<ul>
  <li>Agregar descripciones a endpoints con <code>@Operation</code>.</li>
  <li>Usar ejemplos de modelos en DTOs.</li>
</ul>

<h3>Ejercicio</h3>
<p>Configura Swagger en tu proyecto e identifica en <code>/swagger-ui.html</code> todos los endpoints disponibles.</p>
' WHERE title = 'Documentación con Swagger';

-- Módulo 5: Seguridad básica
UPDATE module SET content = '
<h2>Seguridad Básica en APIs con Spring Security</h2>
<p>Spring Security provee autenticación y autorización a nivel de endpoints.</p>

<h3>Conceptos Clave</h3>
<ul>
  <li><b>Autenticación:</b> verificar identidad (ej. usuario y contraseña).</li>
  <li><b>Autorización:</b> verificar permisos (ej. rol ADMIN).</li>
  <li><b>Filtros de seguridad:</b> interceptan cada petición HTTP.</li>
</ul>

<h3>Ejemplo de Configuración</h3>
<pre><code>@Bean
public SecurityFilterChain security(HttpSecurity http) throws Exception {
  return http
    .authorizeHttpRequests(auth -> auth
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated()
    )
    .formLogin(withDefaults())
    .build();
}
</code></pre>

<h3>Buenas Prácticas</h3>
<ul>
  <li>No exponer credenciales en el código.</li>
  <li>Usar JWT para APIs sin estado (stateless).</li>
  <li>Restringir endpoints sensibles solo a roles autorizados.</li>
</ul>

<h3>Ejercicio</h3>
<p>Agrega seguridad básica a tu API: protege la ruta <code>/api/admin/**</code> para que solo accedan usuarios con rol ADMIN.</p>
' WHERE title = 'Seguridad básica';
