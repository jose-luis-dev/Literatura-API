package com.LiteraturaAPI.exception.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "error", "message"})
public class ErrorResponse {
    private final boolean success;
    private final String error;
    private final String message;

    public ErrorResponse(String error, String message){
        this.success = false;
        this.error = error;
        this.message = message;
    }
}
