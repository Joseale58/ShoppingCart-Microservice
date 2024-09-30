package com.emazon.shopping_service.infrastructure.output.feign.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
