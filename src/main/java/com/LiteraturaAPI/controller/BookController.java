package com.LiteraturaAPI.controller;

import com.LiteraturaAPI.model.BookData;
import com.LiteraturaAPI.model.BookResponse;
import com.LiteraturaAPI.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Buscar titulo del libro
    @GetMapping("/search")
    public List<BookData> buscar (@RequestParam String titulo){
        BookResponse respuesta = bookService.buscarPorTitulo(titulo);

        if (respuesta == null || respuesta.resultados() == null) {
            System.out.println("No se encontraron libros con este titulo.");
            return List.of();
        }
        return respuesta.resultados();

    }


}
