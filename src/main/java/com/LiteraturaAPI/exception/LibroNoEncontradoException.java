package com.LiteraturaAPI.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(String message){
        super(message);
    }
}
