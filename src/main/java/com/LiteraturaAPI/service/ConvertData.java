package com.LiteraturaAPI.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConvertData implements IConvertData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir el JSON", e);
        }
    }
}
