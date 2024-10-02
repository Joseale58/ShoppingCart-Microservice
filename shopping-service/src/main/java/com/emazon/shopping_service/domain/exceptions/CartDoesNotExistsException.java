package com.emazon.shopping_service.domain.exceptions;

public class CartDoesNotExistsException extends RuntimeException {
    public CartDoesNotExistsException(String message) {
        super(message);
    }
}
