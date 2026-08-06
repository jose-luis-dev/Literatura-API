package com.LiteraturaAPI.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookResponse(
        @JsonAlias("count") Integer total,
        @JsonAlias("results") List<BookData> resultados)
{}
