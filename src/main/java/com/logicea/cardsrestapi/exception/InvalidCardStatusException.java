package com.logicea.cardsrestapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidCardStatusException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

    public InvalidCardStatusException(HttpStatus status,String message){
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
