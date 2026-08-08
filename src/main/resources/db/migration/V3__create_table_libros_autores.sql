CREATE TABLE libros_autores (
    libro_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY (libro_id, autor_id),
    FOREIGN KEY (libro_id)
        REFERENCES libros(id),
    FOREIGN KEY (autor_id)
        REFERENCES autores(id)
);