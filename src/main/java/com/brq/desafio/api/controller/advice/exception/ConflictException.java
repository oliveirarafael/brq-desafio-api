package com.brq.desafio.api.controller.advice.exception;

public class ConflictException extends RuntimeException{

    private static final long serialVersionUID = 1303426879914266545L;

    public ConflictException(String message) {
        super(message);
    }

}
