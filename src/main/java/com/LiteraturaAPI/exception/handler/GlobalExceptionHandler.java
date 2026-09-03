package com.LiteraturaAPI.exception.handler;

import com.LiteraturaAPI.exception.AutorNoEncontradoException;
import com.LiteraturaAPI.exception.GutendexApiException;
import com.LiteraturaAPI.exception.LibroNoEncontradoException;
import com.LiteraturaAPI.exception.LibroYaExisteException;
import com.LiteraturaAPI.exception.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Argumento inválido
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException e){
        return new ErrorResponse("BAD_REQUEST", "El parámetro: '" + e.getName() +
                "' tiene un formato inválido.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse parametroFaltante(MissingServletRequestParameterException e){
        return new ErrorResponse("BAD_REQUEST", "Falta el parámetro requerido: '" + e.getMessage() + "'");
    }

    // 404 para libro no encontrado
    @ExceptionHandler(LibroNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse libroNoEncontrado(
            LibroNoEncontradoException e){
        return new ErrorResponse("NOT_FOUND", e.getMessage());
    }

    // 404 para autor no encontrado
    @ExceptionHandler(AutorNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse autorNoEncontrado(
            AutorNoEncontradoException e){
        return new ErrorResponse("NOT_FOUND", e.getMessage());
    }

    // 409 - Duplicado de libro
    @ExceptionHandler(LibroYaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse libroYaExistente(
            LibroYaExisteException e){
        return new ErrorResponse("CONFLICT", e.getMessage());
    }

    // 409 - libro duplicado concurrente
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse libroDuplicadoConcurrente(DataIntegrityViolationException e){
        return new ErrorResponse("CONFLICT", "El libro ya se encuentra en la DB");
    }

    // 502 - Fallo al comunicarse con la API externa
    @ExceptionHandler(GutendexApiException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse errorApiExterna(GutendexApiException e){
        return new ErrorResponse("BAD_GATEWAY", "No se pudo obtener información desde el servicio externo.");
    }


    // 500 Error general
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception e){
        return new ErrorResponse("INTERNAL_ERROR", "Error interno del servidor");
    }

}
