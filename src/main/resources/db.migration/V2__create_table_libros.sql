CREATE TABLE libros (
    id BIGSERIAL PRIMARY KEY,
    gutendex_id INT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    idioma VARCHAR(10),
    numero_descargas INT
);