package com.LiteraturaAPI.service;

import com.LiteraturaAPI.model.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private GutendexClient gutendexClient;

    @Autowired
    private ConvertData convertData;

    public BookResponse buscarPorTitulo (String titulo){
        String url = "/books/?search=" + titulo.replace(" ","+");
        String json = gutendexClient.fetch(url);

        return convertData.getData(json, BookResponse.class);
    }

}
