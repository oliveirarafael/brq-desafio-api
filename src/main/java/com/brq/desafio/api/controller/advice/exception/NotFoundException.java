package com.brq.desafio.api.controller.advice.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1303426879914266545L;

    public NotFoundException(String message) {
        super(message);
    }

}