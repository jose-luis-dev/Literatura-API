package com.LiteraturaAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexClient {

    @Value("${gutendex.api.baseurl}")
    private String baseUrl;

    private final HttpClient client = HttpClient.newHttpClient();

    public String fetch(String endpoint){
        String url = baseUrl + endpoint;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200){
            throw new RuntimeException(
                    "Error al consumir API. Código: " + response.statusCode());
        }
        return response.body();
    }
}
