package com.LiteraturaAPI.service;


import com.LiteraturaAPI.exception.GutendexApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class ConvertData implements IConvertData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        }catch (JsonProcessingException e){
            log.error("Error al deserializar JSON hacia la clase {}: {}", clase.getSimpleName(), e.getMessage(), e);
            throw new GutendexApiException("Error al convertir el JSON", e);
        }
    }
}
