package com.emazon.shopping_service.infrastructure.output.feign.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
