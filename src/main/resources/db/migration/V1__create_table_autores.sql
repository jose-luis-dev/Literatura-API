--- Primero se crea la tabla autores ya que no depende de llaves foraneas. ---
CREATE TABLE autores (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_nacimiento INT,
    fecha_fallecimiento INT
);