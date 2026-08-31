package com.LiteraturaAPI.controller;

import com.LiteraturaAPI.dto.AuthorDTO;
import com.LiteraturaAPI.exception.AutorNoEncontradoException;
import com.LiteraturaAPI.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(
            AuthorService authorService
    ){
        this.authorService = authorService;
    }

    // Listar autores
    @GetMapping
    public List<AuthorDTO> listarAutores(){
        return authorService.listarAutores();
    }

    // Buscar desde DB autores de libro
    @GetMapping("/author")
    public ResponseEntity<AuthorDTO> buscarEnDBPorAutor(@RequestParam String nombre){
        return authorService.buscarEnDBPorAutor(nombre)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new AutorNoEncontradoException(
                        "No existe autor con nombre: " + nombre
                ));
    }

    @GetMapping("/vivos")
    public List<AuthorDTO> autoresVivosEn(@RequestParam Integer año){
        return authorService.autoresVivosEn(año);
    }
}
