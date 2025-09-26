-- ================================
-- Curso: Fundamentos Cloud
-- ================================

-- Módulo 1: Conceptos de Cloud
UPDATE module SET content = '
<h2>Conceptos de Cloud</h2>
<p>La computación en la nube permite acceder a recursos (servidores, almacenamiento, bases de datos, redes, software) a través de Internet bajo demanda.</p>

<h3>Modelos de Servicio</h3>
<ul>
  <li><b>IaaS (Infraestructura como Servicio):</b> Recursos básicos como servidores virtuales y redes. Ejemplo: AWS EC2, Google Compute Engine.</li>
  <li><b>PaaS (Plataforma como Servicio):</b> Entorno gestionado para desarrollar y desplegar aplicaciones. Ejemplo: Heroku, AWS Elastic Beanstalk.</li>
  <li><b>SaaS (Software como Servicio):</b> Aplicaciones listas para usar. Ejemplo: Gmail, Salesforce.</li>
</ul>

<h3>Ventajas</h3>
<ul>
  <li>Escalabilidad bajo demanda.</li>
  <li>Pago por uso.</li>
  <li>Alta disponibilidad y redundancia.</li>
  <li>Despliegues más rápidos.</li>
</ul>

<h3>Ejercicio</h3>
<p>Dibuja un esquema comparando un centro de datos tradicional con la nube (IaaS, PaaS, SaaS).</p>
' WHERE title = 'Conceptos de Cloud';


-- Módulo 2: AWS Basics
UPDATE module SET content = '
<h2>AWS Basics</h2>
<p>Amazon Web Services (AWS) es la plataforma de nube pública más utilizada, con más de 200 servicios.</p>

<h3>Servicios Clave</h3>
<ul>
  <li><b>EC2:</b> Servidores virtuales escalables.</li>
  <li><b>S3:</b> Almacenamiento de objetos seguro y escalable.</li>
  <li><b>RDS:</b> Base de datos relacional administrada (MySQL, PostgreSQL, etc.).</li>
  <li><b>Lambda:</b> Funciones serverless que se ejecutan bajo demanda.</li>
</ul>

<h3>Ejemplo</h3>
<p>Crear una instancia EC2 con Ubuntu, instalar Nginx y desplegar una aplicación.</p>

<h3>Buenas prácticas</h3>
<ul>
  <li>Configura grupos de seguridad (firewalls virtuales).</li>
  <li>Usa IAM Roles en lugar de credenciales estáticas.</li>
  <li>Habilita backups automáticos en RDS.</li>
</ul>

<h3>Ejercicio</h3>
<p>Crea un bucket en S3 y sube un archivo. Luego genera un enlace público temporal (presigned URL).</p>
' WHERE title = 'AWS Basics';


-- Módulo 3: Azure Basics
UPDATE module SET content = '
<h2>Azure Basics</h2>
<p>Microsoft Azure es la segunda plataforma de nube pública más grande del mundo.</p>

<h3>Servicios Clave</h3>
<ul>
  <li><b>Azure Virtual Machines:</b> Infraestructura como servicio.</li>
  <li><b>App Service:</b> Plataforma gestionada para aplicaciones web.</li>
  <li><b>Blob Storage:</b> Almacenamiento de objetos.</li>
  <li><b>Azure SQL Database:</b> Base de datos relacional administrada.</li>
</ul>

<h3>Ejemplo</h3>
<p>Desplegar una aplicación web en Azure App Service con integración continua desde GitHub.</p>

<h3>Buenas prácticas</h3>
<ul>
  <li>Organiza recursos usando Resource Groups.</li>
  <li>Aplica etiquetas (tags) para gestión de costos.</li>
  <li>Usa Azure Monitor para métricas y alertas.</li>
</ul>

<h3>Ejercicio</h3>
<p>Crea un contenedor en Blob Storage y sube un archivo. Luego prueba accederlo con un token SAS.</p>
' WHERE title = 'Azure Basics';


-- Módulo 4: Google Cloud Basics
UPDATE module SET content = '
<h2>Google Cloud Basics</h2>
<p>Google Cloud Platform (GCP) es conocido por sus servicios de datos y machine learning.</p>

<h3>Servicios Clave</h3>
<ul>
  <li><b>Compute Engine:</b> Máquinas virtuales escalables.</li>
  <li><b>Cloud Storage:</b> Almacenamiento seguro y duradero.</li>
  <li><b>BigQuery:</b> Análisis de datos masivos en segundos.</li>
  <li><b>Cloud Functions:</b> Computación sin servidor.</li>
</ul>

<h3>Ejemplo</h3>
<p>Subir un dataset a BigQuery y ejecutar una consulta SQL sobre millones de registros.</p>

<h3>Buenas prácticas</h3>
<ul>
  <li>Habilita Cloud Identity & Access Management (IAM).</li>
  <li>Configura presupuestos y alertas para controlar costos.</li>
  <li>Aplica políticas de red con VPC Service Controls.</li>
</ul>

<h3>Ejercicio</h3>
<p>Crea una función en Cloud Functions que se ejecute cuando se suba un archivo a Cloud Storage.</p>
' WHERE title = 'Google Cloud Basics';


-- Módulo 5: DevOps & CI/CD
UPDATE module SET content = '
<h2>DevOps & CI/CD</h2>
<p>DevOps es una cultura que integra desarrollo y operaciones, y CI/CD automatiza la entrega de software.</p>

<h3>Conceptos Clave</h3>
<ul>
  <li><b>CI (Integración Continua):</b> Validar cada cambio en el código mediante pruebas automáticas.</li>
  <li><b>CD (Entrega/Despliegue Continuo):</b> Automatizar el paso de la aplicación a producción.</li>
  <li><b>Infraestructura como código:</b> Terraform, CloudFormation.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code># GitHub Actions workflow
name: CI/CD Pipeline
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Build with Maven
      run: mvn clean install
    - name: Deploy to AWS Elastic Beanstalk
      run: eb deploy
</code></pre>

<h3>Ejercicio</h3>
<p>Crea un pipeline simple que compile una aplicación y la despliegue automáticamente en la nube.</p>
' WHERE title = 'DevOps & CI/CD';
