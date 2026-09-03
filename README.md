# 📚 LiteraturaAPI

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.7-brightgreen?style=flat&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?style=flat&logo=postgresql)
![Flyway](https://img.shields.io/badge/Flyway-migrations-red?style=flat&logo=flyway)
![Maven](https://img.shields.io/badge/Maven-3.9+-red?style=flat&logo=apachemaven)


API REST que consume la API pública [Gutendex](https://gutendex.com/) para buscar libros y autores del catálogo del Proyecto Gutenberg, y persiste los resultados en una base de datos PostgreSQL para consultas posteriores.

Proyecto desarrollado como parte del **Challenge Backend de [Alura Latam](https://www.alura.com.mx/)** (programa ONE - Oracle Next Education).

**Insignia obtenida:** Challenge completado y certificado por Alura Latam.

## 📖 Descripción

LiteraturaAPI permite:

- Buscar libros por título directamente en la API externa de Gutendex.
- Guardar el libro encontrado (junto con su autor) en la base de datos local.
- Listar todos los libros guardados.
- Buscar un libro guardado por título.
- Listar libros filtrados por idioma.
- Contar cuántos libros hay registrados en un idioma determinado.
- Listar todos los autores guardados.
- Buscar un autor por nombre.
- Listar autores que estaban vivos en un año determinado.

> **Nota de alcance:** siguiendo las reglas del challenge, cuando un libro tiene varios autores o idiomas registrados en Gutendex, el proyecto conserva únicamente el primero de cada lista, para simplificar el modelo de datos.

---

## 🛠️ Tecnologías

- **Java 17+**
- **Spring Boot** (Web, Data JPA)
- **PostgreSQL**
- **Flyway** — control de versiones de la base de datos
- **Lombok**
- **Jackson** — deserialización del JSON de Gutendex
- **Java `HttpClient`** — consumo de la API externa (sin librerías adicionales)
- **Maven**

---
## 🏗️ Arquitectura

Estructura organizada por capas, siguiendo separación de responsabilidades:

```
com.LiteraturaAPI
├── controller       # Endpoints REST (AuthorController, BookController)
├── dto              # Objetos de transferencia expuestos al cliente
├── exception        # Excepciones de negocio + manejo global de errores
│   ├── dto          # ErrorResponse
│   └── handler      # GlobalExceptionHandler
├── mapper           # Conversión Entity -> DTO
├── model
│   ├── entity       # Entidades JPA (Author, Book)
│   └── ...          # Records de la respuesta de Gutendex (BookData, AuthorData, BookResponse)
├── repository       # Interfaces Spring Data JPA
└── service          # Lógica de negocio y consumo de la API externa
```
### Modelo de datos

- **Author** `1 ── N` **Book** (`@OneToMany` / `@ManyToOne`), relación gestionada vía `autor_id`.
- Migraciones versionadas con Flyway (`V1`: tabla `autores`, `V2`: tabla `libros`, `V3`: constraint de unicidad sobre `gutendex_id`).

### Tabla de Códigos

Respuestas de error consistentes vía `@RestControllerAdvice`, con un formato único (`ErrorResponse`):

| Código | Significado               | Caso / Descripción                                                                         |
|--------|---------------------------|--------------------------------------------------------------------------------------------|
| `200`  | **OK**                    | Petición exitosa (Consulta).                                                               |
| `201`  | **Created**               | Recurso creado exitosamente en la base de datos.                                           |
| `400`  | **Bad Request**           | Parámetro con formato inválido o parámetro requerido faltante.                             |
| `404`  | **Not Found**             | Libro no encontrado / Autor no encontrado.                                                 |
| `409`  | **Conflict**              | El libro ya existe en la base de datos (validación de aplicación + constraint único en DB) |
| `500`  | **Internal Server Error** | Error interno no controlado                                                                |
| `502`  | **Bad Gateway**           | Fallo al obtener información de la API Externa                                             |

Todas las respuestas con estado `4xx` y `5xx` devuelven el siguiente formato JSON estandarizado:

```json
{
  "status": 400,
  "success": false,
  "error": "Bad Request",
  "message": "Mensaje descriptivo del error"
}
```
> **Nota:** La propiedad `error` se incluye únicamente cuando existen detalles específicos a nivel de campo (por ejemplo, en errores de validación HTTP `400`).

---
## 🔌 Endpoints principales

### Libros

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/books/search?titulo=` | Busca el libro en la API externa de Gutendex |
| `POST` | `/books?titulo=` | Busca y guarda el primer resultado en la base de datos |
| `GET` | `/books` | Lista todos los libros guardados |
| `GET` | `/books/titulo?titulo=` | Busca un libro guardado por título |
| `GET` | `/books/idioma?idioma=` | Lista libros guardados por idioma |
| `GET` | `/books/count?idioma=` | Cuenta libros guardados por idioma |

### Autores

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/authors` | Lista todos los autores guardados |
| `GET` | `/authors/author?nombre=` | Busca un autor guardado por nombre |
| `GET` | `/authors/vivos?año=` | Lista autores vivos en un año determinado |

---

## ⚙️ Configuración y ejecución

### Requisitos previos

- Java 17+
- PostgreSQL en ejecución
- Maven

### Variables de entorno

El proyecto lee la configuración de la base de datos desde variables de entorno:

| Variable | Descripción |
|---|---|
| `DB_HOST` | Host de PostgreSQL |
| `DB_USER` | Usuario de la base de datos |
| `DB_PASSWORD` | Contraseña de la base de datos |

Puedes usar `application-example.yml` como referencia para tu propia configuración local.

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/jose-luis-dev/Literatura-API.git
cd literatura-api

# 2. Crear la base de datos en PostgreSQL
createdb literatura_db

# 3. Definir las variables de entorno (o exportarlas en tu shell)
export DB_HOST=localhost
export DB_USER=tu_usuario
export DB_PASSWORD=tu_password

# 4. Ejecutar el proyecto (Flyway aplica las migraciones automáticamente)
./mvnw spring-boot:run
```

La API quedará disponible en `http://localhost:8080`.

---

## 🧪 Decisiones de diseño destacadas

- **`ddl-auto: validate` + Flyway**: el esquema de la base de datos es la fuente de verdad; Hibernate solo valida contra las migraciones, nunca genera ni modifica el esquema automáticamente.
- **Perfiles de Spring (`dev`)**: el logging detallado de SQL (`show-sql`, `TRACE` de parámetros bindeados) está aislado en `application-dev.yml`, evitando exponer datos sensibles en un entorno productivo.
- **Constraint único en `gutendex_id`**: además de la validación en la capa de servicio, la base de datos garantiza la integridad ante condiciones de carrera (peticiones concurrentes para el mismo libro).
- **Records inmutables** para las respuestas de la API externa (`BookData`, `AuthorData`, `BookResponse`) con `@JsonIgnoreProperties(ignoreUnknown = true)`, para tolerar cambios futuros en el schema de Gutendex sin romper la deserialización.

---

## 👨‍💻 Autor

[![LinkedIn](https://img.shields.io/badge/LinkedIn-jose--alvarado--devdata-blue?style=flat&logo=linkedin)](https://linkedin.com/in/jose-alvarado-devdata)
[![GitHub](https://img.shields.io/badge/GitHub-jose--luis--dev-black?style=flat&logo=github)](https://github.com/jose-luis-dev)

Desarrollado por **Luis** ([@jose-luis-dev](https://github.com/jose-luis-dev)) como parte del proceso de formación en el Challenge Backend de Alura Latam.