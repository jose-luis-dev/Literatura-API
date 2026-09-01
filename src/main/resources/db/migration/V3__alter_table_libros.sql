-- V3 agregar_unique_gutendex_id.sql
ALTER TABLE libros
ADD CONSTRAINT uk_libros_gutendex_id UNIQUE (gutendex_id);