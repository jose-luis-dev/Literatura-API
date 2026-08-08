package com.LiteraturaAPI.controller;


import com.LiteraturaAPI.model.BookResponse;
import com.LiteraturaAPI.service.ConvertData;
import com.LiteraturaAPI.service.GutendexClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final GutendexClient gutendexClient;
    private final ConvertData convertData;

    public TestController (GutendexClient gutendexClient, ConvertData convertData) {
        this.gutendexClient = gutendexClient;
        this.convertData = convertData;
    }

    @GetMapping
    public BookResponse test() {

        String json = gutendexClient.fetch("/books/?search=Java");
        return convertData.getData(json,BookResponse.class);

    }


}
