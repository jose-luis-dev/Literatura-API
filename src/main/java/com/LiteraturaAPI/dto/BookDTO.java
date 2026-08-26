package com.LiteraturaAPI.dto;

public record BookDTO(
                      Integer idLibro,
                      String titulo,
                      String idioma,
                      Integer numeroDescargas,
                      AuthorDTO autor
) {}
