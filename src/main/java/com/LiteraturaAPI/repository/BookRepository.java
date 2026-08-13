package com.LiteraturaAPI.repository;

import com.LiteraturaAPI.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Libro, Long> {


    Optional<Libro> findByIdLibro(Integer idLibro);
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdiomaIgnoreCase(String idioma);
    long countByIdiomaIgnoreCase(String idioma);
}
