package com.LiteraturaAPI.service;

import com.LiteraturaAPI.model.AuthorData;
import com.LiteraturaAPI.model.BookData;
import com.LiteraturaAPI.model.entity.Autor;
import com.LiteraturaAPI.model.entity.Libro;
import com.LiteraturaAPI.repository.AuthorRepository;
import com.LiteraturaAPI.repository.BookRepository;
import org.springframework.stereotype.Service;

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

    public void guardarLibroPorTitulo(String titulo) {
        var response = bookService.buscarPorTitulo(titulo);

        if (response.resultados() == null || response.resultados().isEmpty()) {
            System.out.println("No se encontraron libros con este titulo.");
            return;
        }

        // Obtenemos el primer resultado del libro buscado
        BookData bookData = response.resultados().get(0);

        // Validamos que tenga autor
        if (bookData.autores() == null || bookData.autores().isEmpty()) {
            System.out.println("El libro no tiene autores registrados.");
            return;
        }

        // Obtener el primer autor e idioma
        AuthorData authorData = bookData.autores().get(0);
        String idioma = (bookData.idiomas() != null && !bookData.idiomas().isEmpty())
                ? bookData.idiomas().get(0) : "Desconocido";

        // Validamos si existe autor en la DB
        Autor autor = authorRepository.findByNombreIgnoreCase(authorData.autor())
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor();
                    nuevoAutor.setNombre(authorData.autor());
                    nuevoAutor.setFechaNacimiento(authorData.fechaNacimiento());
                    nuevoAutor.setFechaFallecimiento(authorData.fechaFallecimiento());

                    return authorRepository.save(nuevoAutor);
                });

        // Crear el libro
        // Validamos si existe libro en la DB
        Optional<Libro> libroExistente = bookRepository.findByIdLibro(bookData.idLibro());
        if (libroExistente.isPresent()){
            System.out.println("El libro '" + bookData.titulo() + "' ya se encuentra registrado en la DB");
            return;
        }
        Libro nuevolibro = new Libro();
        nuevolibro.setIdLibro(bookData.idLibro());
        nuevolibro.setTitulo(bookData.titulo());
        String idiomas = String.join(",", bookData.idiomas());
        nuevolibro.setIdioma(idiomas);
        nuevolibro.setNumeroDescargas(bookData.numeroDescargas());
        nuevolibro.setAutor(autor);

        bookRepository.save(nuevolibro);
    }
}
