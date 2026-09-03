package com.LiteraturaAPI.service;

import com.LiteraturaAPI.dto.BookDTO;
import com.LiteraturaAPI.mapper.BookMapper;
import com.LiteraturaAPI.model.BookResponse;
import com.LiteraturaAPI.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final GutendexClient gutendexClient;
    private final ConvertData convertData;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(
            GutendexClient gutendexClient,
            ConvertData convertData,
            BookRepository bookRepository,
            BookMapper bookMapper
    ){
        this.gutendexClient = gutendexClient;
        this.convertData = convertData;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    // libro por título desde API Externa
    public BookResponse buscarEnGutendexPorTitulo (String titulo){
        String tituloEncoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = "/books/?search=" + tituloEncoded;
        String json = gutendexClient.fetch(url);

        return convertData.getData(json, BookResponse.class);
    }

    // Listar libros desde la DB
    public List<BookDTO> listarLibros(){
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    // Buscar título de libros desde la DB
    public Optional<BookDTO> buscarEnBDPorTitulo(String titulo){
        return bookRepository.findByTituloIgnoreCase(titulo)
                .map(bookMapper::toDTO);
    }

    // buscar libros por idioma
    public List<BookDTO> buscarPorIdioma(String idioma){
        return bookRepository.findByIdiomaIgnoreCase(idioma)
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    // Cantidad de libros por idioma
    public long contarPorIdioma(String idioma ){
        return bookRepository.countByIdiomaIgnoreCase(idioma);
    }

}
