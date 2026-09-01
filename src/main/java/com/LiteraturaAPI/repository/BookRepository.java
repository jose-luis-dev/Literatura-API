package com.LiteraturaAPI.repository;

import com.LiteraturaAPI.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {


    Optional<Book> findByIdLibro(Integer idLibro);
    Optional<Book> findByTituloIgnoreCase(String titulo);
    List<Book> findByIdiomaIgnoreCase(String idioma);
    long countByIdiomaIgnoreCase(String idioma);
}
