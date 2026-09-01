package com.LiteraturaAPI.mapper;

import com.LiteraturaAPI.dto.AuthorDTO;
import com.LiteraturaAPI.model.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDTO toDTO(Author autor){
        return new AuthorDTO(
                autor.getNombre(),
                autor.getFechaNacimiento(),
                autor.getFechaFallecimiento()
        );
    }
}
