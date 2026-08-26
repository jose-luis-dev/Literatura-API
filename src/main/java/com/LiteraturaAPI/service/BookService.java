package com.LiteraturaAPI.service;

import com.LiteraturaAPI.dto.BookDTO;
import com.LiteraturaAPI.mapper.BookMapper;
import com.LiteraturaAPI.model.BookResponse;
import com.LiteraturaAPI.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private GutendexClient gutendexClient;

    @Autowired
    private ConvertData convertData;


    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper
    ){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    // Busqueda de libro por titulo
    public BookResponse buscarPorTitulo (String titulo){
        String url = "/books/?search=" + titulo.replace(" ","+");
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

}
