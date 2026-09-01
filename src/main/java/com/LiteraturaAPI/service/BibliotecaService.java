package com.LiteraturaAPI.service;


import com.LiteraturaAPI.exception.AutorNoEncontradoException;
import com.LiteraturaAPI.exception.LibroNoEncontradoException;
import com.LiteraturaAPI.exception.LibroYaExisteException;
import com.LiteraturaAPI.model.AuthorData;
import com.LiteraturaAPI.model.BookData;
import com.LiteraturaAPI.model.entity.Author;
import com.LiteraturaAPI.model.entity.Book;
import com.LiteraturaAPI.repository.AuthorRepository;
import com.LiteraturaAPI.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BibliotecaService {


    private final BookService bookService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BibliotecaService(
            BookService bookService,
            BookRepository bookRepository,
            AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }

    @Transactional
    public void guardarLibroPorTitulo(String titulo) {
        var response = bookService.buscarEnGutendexPorTitulo(titulo);

        if (response.resultados() == null || response.resultados().isEmpty()) {
            throw new LibroNoEncontradoException("No se encontraron libros con este titulo.");
        }

        // Obtenemos el primer resultado del libro buscado
        BookData bookData = response.resultados().get(0);

        // Validamos que tenga autor
        if (bookData.autores() == null || bookData.autores().isEmpty()) {
            throw new AutorNoEncontradoException("El libro ´" + bookData.titulo() + "' no tiene autores registrados.");
        }

        // Obtenemos el primer resultado de idioma.
        String idioma = (bookData.idiomas() != null && !bookData.idiomas().isEmpty())
                ? bookData.idiomas().get(0) : "Desconocido";

        // Validamos si existe libro en la DB
        Optional<Book> libroExistente = bookRepository.findByIdLibro(bookData.idLibro());
        if (libroExistente.isPresent()){
            throw  new LibroYaExisteException("El libro '" + bookData.titulo() + "' ya se encuentra registrado en la DB");
        }

        // ---Si no hay Libro y autores en la DB--- //
        // Obtener el primer autor e idioma
        AuthorData authorData = bookData.autores().get(0);

        // Validamos si existe autor en la DB
        Author autor = authorRepository.findByNombreIgnoreCase(authorData.autor())
                .orElseGet(() -> {
                    Author nuevoAutor = new Author();
                    nuevoAutor.setNombre(authorData.autor());
                    nuevoAutor.setFechaNacimiento(authorData.fechaNacimiento());
                    nuevoAutor.setFechaFallecimiento(authorData.fechaFallecimiento());
                    return authorRepository.save(nuevoAutor);
                });

        // Crear el libro
        Book nuevoLibro = new Book();
        nuevoLibro.setIdLibro(bookData.idLibro());
        nuevoLibro.setTitulo(bookData.titulo());
        nuevoLibro.setIdioma(idioma);
        nuevoLibro.setNumeroDescargas(bookData.numeroDescargas());
        nuevoLibro.setAutor(autor);

        bookRepository.save(nuevoLibro);

    }

}
