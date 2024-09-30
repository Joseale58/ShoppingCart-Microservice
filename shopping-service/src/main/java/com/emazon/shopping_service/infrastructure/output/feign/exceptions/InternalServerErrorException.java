package com.emazon.shopping_service.infrastructure.output.feign.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
