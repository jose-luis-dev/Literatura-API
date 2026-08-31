package com.LiteraturaAPI.controller;

import com.LiteraturaAPI.dto.BookDTO;
import com.LiteraturaAPI.exception.LibroNoEncontradoException;
import com.LiteraturaAPI.model.BookData;
import com.LiteraturaAPI.model.BookResponse;
import com.LiteraturaAPI.service.BibliotecaService;
import com.LiteraturaAPI.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BibliotecaService bibliotecaService;

    public BookController (
            BookService bookService,
            BibliotecaService bibliotecaService){
        this.bookService = bookService;
        this.bibliotecaService = bibliotecaService;
    }

    // Buscar título del libro desde API Externa
    @GetMapping("/search")
    public ResponseEntity<List<BookData>> buscar (@RequestParam String titulo){
        BookResponse respuesta = bookService.buscarEnGutendexPorTitulo(titulo);

        if (respuesta == null || respuesta.resultados() == null || respuesta.resultados().isEmpty()) {

            throw new LibroNoEncontradoException(
                    "No se encontraron libros con el título: " + titulo
            );
        }
        return ResponseEntity.ok(respuesta.resultados());
    }

    // Buscar y guardar el primer libro encontrado.
    @PostMapping
    public ResponseEntity<String> guardarLibroPorTitulo(@RequestParam String titulo) {
        bibliotecaService.guardarLibroPorTitulo(titulo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Libro con titulo '" + titulo + "' guardado correctamente en la DB.");
        }

        // Listar Libros
        @GetMapping
        public List<BookDTO> listarLibros(){
            return bookService.listarLibros();
        }

        // Buscar desde DB título de libro
        @GetMapping("/titulo")
        public ResponseEntity<BookDTO> buscarEnDBPorTitulo(@RequestParam String titulo){

            return bookService.buscarEnBDPorTitulo(titulo)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new LibroNoEncontradoException(
                            "No existe un libro con el título: " + titulo
                    ));
        }

        // Libros por idioma
        @GetMapping("/idioma")
        public List<BookDTO> buscarPorIdioma(@RequestParam String idioma){
            return bookService.buscarPorIdioma(idioma);
        }

        // Cantidad de libros por idioma
        @GetMapping("/count")
        public long contarPorIdioma(@RequestParam String idioma){
            return bookService.contarPorIdioma(idioma);
        }

    }




