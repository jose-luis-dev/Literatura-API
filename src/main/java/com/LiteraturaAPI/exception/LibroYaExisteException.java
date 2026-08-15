package com.LiteraturaAPI.exception;

public class LibroYaExisteException extends RuntimeException {
    public LibroYaExisteException (String message){
        super(message);
    }
}
