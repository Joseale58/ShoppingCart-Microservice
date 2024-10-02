package com.emazon.shopping_service.domain.exceptions;

public class InsufficientAddedItemsToCartException extends RuntimeException {
    public InsufficientAddedItemsToCartException(String message) {
        super(message);
    }
}
