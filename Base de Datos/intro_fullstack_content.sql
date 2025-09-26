-- ================================
-- Curso: Intro Fullstack
-- ================================

-- Módulo 1: HTML & CSS
UPDATE module SET content = '
<h2>HTML & CSS</h2>
<p>HTML define la estructura de una página web y CSS controla su estilo.</p>

<h3>Conceptos Clave</h3>
<ul>
  <li><b>HTML:</b> etiquetas (<code>&lt;h1&gt;, &lt;p&gt;, &lt;div&gt;</code>) definen bloques de contenido.</li>
  <li><b>CSS:</b> permite aplicar colores, tipografías, márgenes, layouts responsivos.</li>
  <li><b>Box Model:</b> cada elemento tiene <i>margin, border, padding, content</i>.</li>
  <li><b>Flexbox/Grid:</b> sistemas modernos para diseñar layouts adaptables.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>&lt;html&gt;
  &lt;head&gt;&lt;link rel="stylesheet" href="styles.css"&gt;&lt;/head&gt;
  &lt;body&gt;
    &lt;h1&gt;Hola Mundo&lt;/h1&gt;
    &lt;p class="text-red"&gt;Este es un párrafo.&lt;/p&gt;
  &lt;/body&gt;
&lt;/html&gt;
</code></pre>

<h3>Ejercicio</h3>
<p>Crea una landing page con un encabezado, menú, sección principal y pie de página. Dale estilo con Flexbox o Grid.</p>
' WHERE title = 'HTML & CSS';


-- Módulo 2: JavaScript
UPDATE module SET content = '
<h2>JavaScript Básico</h2>
<p>JavaScript permite agregar interactividad a las páginas web.</p>

<h3>Conceptos Clave</h3>
<ul>
  <li>Variables (<code>let, const</code>).</li>
  <li>Funciones y callbacks.</li>
  <li>Manipulación del DOM (<code>document.querySelector</code>).</li>
  <li>Eventos (<code>onclick, addEventListener</code>).</li>
  <li>Promises y async/await para asincronía.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>document.getElementById("btn").addEventListener("click", () => {
  alert("Hola desde JS!");
});</code></pre>

<h3>Ejercicio</h3>
<p>Haz un formulario con un botón que, al hacer clic, valide los datos ingresados y los muestre en pantalla.</p>
' WHERE title = 'JavaScript';


-- Módulo 3: Spring Boot Intro
UPDATE module SET content = '
<h2>Spring Boot Intro</h2>
<p>Spring Boot es un framework que facilita la creación de aplicaciones backend en Java.</p>

<h3>Características</h3>
<ul>
  <li>Configuración automática.</li>
  <li>Servidor embebido (Tomcat).</li>
  <li>Soporte para REST APIs.</li>
  <li>Gestión de dependencias con <code>spring-boot-starter</code>.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code>@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}</code></pre>
' WHERE title = 'Spring Boot Intro';


-- Módulo 4: APIs REST
UPDATE module SET content = '
<h2>APIs REST</h2>
<p>Una API REST es un conjunto de endpoints que exponen datos y operaciones usando HTTP.</p>

<h3>Ejemplo en Spring</h3>
<pre><code>@RestController
@RequestMapping("/api/users")
public class UserController {
  @GetMapping("/{id}")
  public User getUser(@PathVariable Long id) {
    return userService.findById(id);
  }
}</code></pre>

<h3>Ejercicio</h3>
<p>Crea una API para gestionar tareas (ToDo List) con operaciones CRUD.</p>
' WHERE title = 'APIs REST';


-- Módulo 5: Deploy
UPDATE module SET content = '
<h2>Deploy</h2>
<p>El despliegue es el proceso de poner en producción una aplicación.</p>

<h3>Opciones</h3>
<ul>
  <li>Heroku</li>
  <li>AWS Elastic Beanstalk</li>
  <li>Docker + Kubernetes</li>
  <li>Servicios CI/CD (GitHub Actions, GitLab CI)</li>
</ul>

<h3>Ejercicio</h3>
<p>Despliega tu aplicación Spring Boot en Heroku o Render.</p>
' WHERE title = 'Deploy';
