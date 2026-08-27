package com.LiteraturaAPI.controller;

import com.LiteraturaAPI.dto.BookDTO;
import com.LiteraturaAPI.exception.LibroNoEncontradoException;
import com.LiteraturaAPI.exception.LibroYaExisteException;
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

    // Buscar titulo del libro
    @GetMapping("/search")
    public ResponseEntity<List<BookData>> buscar (@RequestParam String titulo){
        BookResponse respuesta = bookService.buscarPorTitulo(titulo);

        if (respuesta == null || respuesta.resultados() == null || respuesta.resultados().isEmpty()) {
            System.out.println("No se encontraron libros con este titulo.");
            return ResponseEntity.notFound().build(); // Devolvemos 404 Not Found si no hay coincidencia
        }
        return ResponseEntity.ok(respuesta.resultados());

    }

    // Buscar y guardar el primer libro encontrado.
    @PostMapping
    public ResponseEntity<String> guardarLibroPorTitulo(@RequestParam String titulo) {
        try {
            bibliotecaService.guardarLibroPorTitulo(titulo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Libro con titulo '" + titulo + "' guardado correctamente en la DB.");
        }catch (LibroNoEncontradoException e) {
            // 404 Not found -- cuando no existe en la API EXTERNA
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (LibroYaExisteException e) {
            // 409 Conflict cuando ya está registrado en la DB
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            // 500 para cualquier error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el libro: " + e.getMessage());
        }
        }

        // Listar Libros
        @GetMapping
        public List<BookDTO> listarLibros(){
            return bookService.listarLibros();
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




