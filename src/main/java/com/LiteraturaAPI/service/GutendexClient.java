package com.LiteraturaAPI.service;

import com.LiteraturaAPI.exception.GutendexApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class GutendexClient {

    @Value("${gutendex.api.baseurl}")
    private String baseUrl;

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public String fetch(String endpoint){
        String url = baseUrl + endpoint;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("Error de red/comunicación al realizar la petición a la URL: {}", url, e);
            throw new GutendexApiException("Error de conexión al consultar la API externa",e);
        } catch (InterruptedException e) {
            log.error("La petición HTTP fue interrumpida en la URL: {}", url, e);
            Thread.currentThread().interrupt();
            throw new GutendexApiException("Petición interrumpida",e);
        }

        int status = response.statusCode();

        if (status < 200 || status >= 300){
            log.error("Respuesta de error desde API Gutendex. URL: {}, HTTP Status: {}, body: {}", url, status, response.body());
            throw new GutendexApiException(
                    "Error al consumir API. Código HTTP: " + status, null );
        }
        return response.body();
    }
}
