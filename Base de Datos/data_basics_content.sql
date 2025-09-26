-- ================================
-- Curso: Data Basics
-- ================================

-- Módulo 1: Introducción a Data
UPDATE module SET content = '
<h2>Introducción a Data</h2>
<p>El mundo actual genera cantidades masivas de datos cada segundo. La ingeniería de datos se encarga de diseñar sistemas que capturen, procesen y gestionen estos datos.</p>

<h3>Conceptos Clave</h3>
<ul>
  <li><b>Dato:</b> Unidad mínima de información (ej. un número, una palabra).</li>
  <li><b>Información:</b> Datos procesados y con contexto.</li>
  <li><b>Conocimiento:</b> Información interpretada que sirve para tomar decisiones.</li>
</ul>

<h3>Tipos de Datos</h3>
<ul>
  <li><b>Estructurados:</b> Filas y columnas (ej. bases de datos relacionales).</li>
  <li><b>No estructurados:</b> Texto, imágenes, videos.</li>
  <li><b>Semi-estructurados:</b> JSON, XML.</li>
</ul>

<h3>Ejercicio</h3>
<p>Haz una tabla clasificando datos que usas en tu día a día (ej. mensajes de WhatsApp, fotos, transacciones bancarias).</p>
' WHERE title = 'Introducción a Data';


-- Módulo 2: SQL Fundamentals
UPDATE module SET content = '
<h2>SQL Fundamentals</h2>
<p>SQL (Structured Query Language) es el lenguaje estándar para gestionar bases de datos relacionales.</p>

<h3>Operaciones Básicas</h3>
<pre><code>-- Crear tabla
CREATE TABLE usuarios (
  id INT PRIMARY KEY,
  nombre VARCHAR(100),
  email VARCHAR(100)
);

-- Insertar datos
INSERT INTO usuarios VALUES (1, "Alice", "alice@mail.com");

-- Consultar datos
SELECT * FROM usuarios;

-- Actualizar datos
UPDATE usuarios SET nombre = "Alicia" WHERE id = 1;

-- Borrar datos
DELETE FROM usuarios WHERE id = 1;
</code></pre>

<h3>Cláusulas Clave</h3>
<ul>
  <li><code>WHERE</code> para filtrar.</li>
  <li><code>JOIN</code> para combinar tablas.</li>
  <li><code>GROUP BY</code> y <code>HAVING</code> para agregaciones.</li>
  <li><code>ORDER BY</code> para ordenar resultados.</li>
</ul>

<h3>Ejercicio</h3>
<p>Crea una tabla de productos con columnas (id, nombre, precio). Inserta 3 productos y haz una consulta que calcule el precio promedio.</p>
' WHERE title = 'SQL Fundamentals';


-- Módulo 3: ETL Basics
UPDATE module SET content = '
<h2>ETL Basics</h2>
<p>ETL significa Extracción, Transformación y Carga. Es el proceso usado para mover datos desde múltiples fuentes hacia un almacén central.</p>

<h3>Fases del Proceso</h3>
<ul>
  <li><b>Extracción:</b> Obtención de datos desde APIs, archivos CSV, bases de datos.</li>
  <li><b>Transformación:</b> Limpieza, normalización, agregaciones, validaciones.</li>
  <li><b>Carga:</b> Almacenar en un Data Warehouse (ej. Snowflake, Redshift).</li>
</ul>

<h3>Herramientas</h3>
<ul>
  <li><b>Ingesta:</b> Apache Kafka, AWS Glue.</li>
  <li><b>Transformación:</b> Apache Spark, dbt.</li>
  <li><b>Carga:</b> Talend, Pentaho.</li>
</ul>

<h3>Ejemplo</h3>
<pre><code># Extracción con Python
import pandas as pd
df = pd.read_csv("ventas.csv")

# Transformación
df["precio_total"] = df["cantidad"] * df["precio_unitario"]

# Carga
df.to_sql("ventas_procesadas", con=engine, if_exists="append")
</code></pre>

<h3>Ejercicio</h3>
<p>Crea un flujo simple que lea un archivo CSV con ventas, calcule el total y lo guarde en una base de datos SQLite.</p>
' WHERE title = 'ETL Basics';


-- Módulo 4: Big Data Concepts
UPDATE module SET content = '
<h2>Big Data Concepts</h2>
<p>Big Data se refiere al manejo de volúmenes masivos de datos que no pueden procesarse con herramientas tradicionales.</p>

<h3>Las 5 V de Big Data</h3>
<ul>
  <li><b>Volumen:</b> Cantidad enorme de datos.</li>
  <li><b>Velocidad:</b> Rapidez de generación y procesamiento.</li>
  <li><b>Variedad:</b> Diferentes formatos (texto, audio, video, logs).</li>
  <li><b>Veracidad:</b> Calidad y confiabilidad de los datos.</li>
  <li><b>Valor:</b> Utilidad de la información extraída.</li>
</ul>

<h3>Herramientas</h3>
<ul>
  <li><b>Hadoop:</b> Procesamiento distribuido.</li>
  <li><b>Spark:</b> Procesamiento en memoria.</li>
  <li><b>Kafka:</b> Procesamiento de flujos.</li>
</ul>

<h3>Ejemplo</h3>
<p>Procesar logs de clics en tiempo real con Kafka + Spark Streaming para detectar tendencias.</p>

<h3>Ejercicio</h3>
<p>Dibuja un pipeline que procese datos desde Twitter en tiempo real y los almacene en un clúster Hadoop.</p>
' WHERE title = 'Big Data Concepts';


-- Módulo 5: Data Visualization
UPDATE module SET content = '
<h2>Data Visualization</h2>
<p>La visualización de datos es el arte de representar información de manera gráfica para facilitar la comprensión.</p>

<h3>Principios</h3>
<ul>
  <li><b>Simplicidad:</b> Menos es más.</li>
  <li><b>Contexto:</b> Proporciona referencias claras.</li>
  <li><b>Comparación:</b> Usa gráficas para mostrar diferencias.</li>
  <li><b>Acción:</b> Facilita la toma de decisiones.</li>
</ul>

<h3>Herramientas</h3>
<ul>
  <li>Power BI</li>
  <li>Tableau</li>
  <li>Matplotlib, Seaborn (Python)</li>
  <li>D3.js (JavaScript)</li>
</ul>

<h3>Ejemplo</h3>
<p>Usar Python y Matplotlib para graficar ventas por mes:</p>
<pre><code>import matplotlib.pyplot as plt

meses = ["Ene", "Feb", "Mar", "Abr"]
ventas = [120, 150, 180, 200]

plt.bar(meses, ventas)
plt.title("Ventas por Mes")
plt.show()
</code></pre>

<h3>Ejercicio</h3>
<p>Crea un dashboard simple que muestre el top 5 de productos más vendidos usando gráficos de barras.</p>
' WHERE title = 'Data Visualization';
