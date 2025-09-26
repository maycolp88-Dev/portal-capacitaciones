-- ================================
-- Curso: Spring Boot Intro
-- ================================

-- Módulo 1: Spring Boot Setup
UPDATE module SET content = '
<h2>Spring Boot Setup</h2>
<p>Spring Boot es un framework de Java que simplifica la creación de aplicaciones basadas en Spring, eliminando gran parte de la configuración manual.</p>

<h3>Pasos Iniciales</h3>
<ol>
  <li>Ir a <a href="https://start.spring.io" target="_blank">start.spring.io</a>.</li>
  <li>Seleccionar proyecto: Maven / Gradle, lenguaje: Java.</li>
  <li>Escoger dependencias (ej. Spring Web, Spring Data JPA, H2).</li>
  <li>Generar el proyecto y abrirlo en tu IDE.</li>
</ol>

<h3>Estructura del Proyecto</h3>
<ul>
  <li><b>src/main/java:</b> Código fuente de la aplicación.</li>
  <li><b>src/main/resources:</b> Archivos de configuración (application.properties).</li>
  <li><b>pom.xml:</b> Dependencias de Maven.</li>
</ul>

<h3>Primer Controlador</h3>
<pre><code>@RestController
public class HelloController {
  @GetMapping("/hello")
  public String hello() {
    return "Hola Spring Boot!";
  }
}
</code></pre>

<h3>Ejercicio</h3>
<p>Crea un proyecto Spring Boot con la dependencia <code>Spring Web</code> y levanta un endpoint <code>/hola</code> que devuelva tu nombre.</p>
' WHERE title = 'Spring Boot Setup';


-- Módulo 2: Controllers
UPDATE module SET content = '
<h2>Spring Boot Controllers</h2>
<p>Los controladores manejan las peticiones HTTP y devuelven respuestas al cliente.</p>

<h3>Anotaciones Clave</h3>
<ul>
  <li><code>@RestController</code> → Indica que la clase expone endpoints REST.</li>
  <li><code>@GetMapping</code> → Para solicitudes GET.</li>
  <li><code>@PostMapping</code> → Para solicitudes POST.</li>
  <li><code>@PutMapping</code> → Para solicitudes PUT.</li>
  <li><code>@DeleteMapping</code> → Para solicitudes DELETE.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>@RestController
@RequestMapping("/api/users")
public class UserController {
  @GetMapping("/{id}")
  public String getUser(@PathVariable Long id) {
    return "Usuario con id " + id;
  }
}
</code></pre>

<h3>Ejercicio</h3>
<p>Define un controlador <code>ProductController</code> con un endpoint GET <code>/products/{id}</code> que devuelva el nombre del producto.</p>
' WHERE title = 'Controllers';


-- Módulo 3: Services
UPDATE module SET content = '
<h2>Services en Spring Boot</h2>
<p>La capa de servicios contiene la lógica de negocio de la aplicación.</p>

<h3>Anotaciones</h3>
<ul>
  <li><code>@Service</code> → Marca una clase como servicio.</li>
  <li><code>@Autowired</code> → Inyecta dependencias automáticamente.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>@Service
public class UserService {
  public String getUserName(Long id) {
    return "Usuario_" + id;
  }
}
</code></pre>

<pre><code>@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public String getUser(@PathVariable Long id) {
    return userService.getUserName(id);
  }
}
</code></pre>

<h3>Ejercicio</h3>
<p>Crea un servicio <code>OrderService</code> que retorne el estado de una orden. Expónlo con un controlador.</p>
' WHERE title = 'Services';


-- Módulo 4: Persistencia con JPA
UPDATE module SET content = '
<h2>Persistencia con JPA</h2>
<p>Spring Data JPA simplifica el acceso a bases de datos al proporcionar repositorios listos para usar.</p>

<h3>Entidad</h3>
<pre><code>@Entity
public class User {
  @Id @GeneratedValue
  private Long id;
  private String name;
}
</code></pre>

<h3>Repositorio</h3>
<pre><code>public interface UserRepository extends JpaRepository&lt;User, Long&gt; {
  Optional&lt;User&gt; findByName(String name);
}
</code></pre>

<h3>Uso en un Servicio</h3>
<pre><code>@Service
public class UserService {
  private final UserRepository repo;
  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public List&lt;User&gt; getAllUsers() {
    return repo.findAll();
  }
}
</code></pre>

<h3>Ejercicio</h3>
<p>Crea una entidad <code>Product</code>, un repositorio y un servicio para listarlos.</p>
' WHERE title = 'Persistencia con JPA';


-- Módulo 5: Testing con JUnit
UPDATE module SET content = '
<h2>Testing con JUnit</h2>
<p>JUnit es el framework estándar de pruebas unitarias en Java.</p>

<h3>Prueba Básica</h3>
<pre><code>import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraTest {
  @Test
  void testSuma() {
    int resultado = 2 + 3;
    assertEquals(5, resultado);
  }
}
</code></pre>

<h3>Pruebas con Spring Boot</h3>
<pre><code>@SpringBootTest
class UserServiceTest {
  @Autowired
  private UserService userService;

  @Test
  void testGetUserName() {
    assertEquals("Usuario_1", userService.getUserName(1L));
  }
}
</code></pre>

<h3>Buenas Prácticas</h3>
<ul>
  <li>Nombrar claramente los tests.</li>
  <li>Probar casos positivos y negativos.</li>
  <li>Usar <code>@BeforeEach</code> y <code>@AfterEach</code> para configuración.</li>
</ul>

<h3>Ejercicio</h3>
<p>Crea una clase <code>MathUtils</code> con un método <code>multiplicar()</code>. Escribe un test unitario con JUnit que valide el resultado.</p>
' WHERE title = 'Testing con JUnit';
