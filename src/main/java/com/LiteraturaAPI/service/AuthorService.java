package com.LiteraturaAPI.service;

import com.LiteraturaAPI.dto.AuthorDTO;
import com.LiteraturaAPI.mapper.AuthorMapper;
import com.LiteraturaAPI.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(
            AuthorRepository authorRepository,
            AuthorMapper authorMapper
    ){
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    // Listar autores desde DB
    public List<AuthorDTO> listarAutores(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    // Buscar autor de libro desde DB
    public Optional<AuthorDTO> buscarEnDBPorAutor(String nombre){
        return authorRepository.findByNombreIgnoreCase(nombre)
                .map(authorMapper::toDTO);
    }


    // Buscar autores vivos por fecha nacimiento
    public List<AuthorDTO> autoresVivosEn(Integer año) {
        return authorRepository
                .findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqualOrFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(
                        año,
                        año,
                        año
                )
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }


}
