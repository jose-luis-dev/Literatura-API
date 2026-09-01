package com.LiteraturaAPI.mapper;

import com.LiteraturaAPI.dto.BookDTO;
import com.LiteraturaAPI.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper){
        this.authorMapper = authorMapper;
    }

    public BookDTO toDTO(Book libro){
        return new BookDTO(
                libro.getIdLibro(),
                libro.getTitulo(),
                libro.getIdioma(),
                libro.getNumeroDescargas(),
                authorMapper.toDTO(libro.getAutor())
        );
    }
}
