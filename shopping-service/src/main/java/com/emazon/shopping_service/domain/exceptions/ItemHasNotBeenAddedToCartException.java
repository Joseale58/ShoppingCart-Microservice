package com.emazon.shopping_service.domain.exceptions;

public class ItemHasNotBeenAddedToCartException extends RuntimeException {
    public ItemHasNotBeenAddedToCartException(String message) {
        super(message);
    }
}
