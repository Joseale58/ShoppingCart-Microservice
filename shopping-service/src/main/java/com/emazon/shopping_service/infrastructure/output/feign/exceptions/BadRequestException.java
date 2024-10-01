package com.emazon.shopping_service.infrastructure.output.feign.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
