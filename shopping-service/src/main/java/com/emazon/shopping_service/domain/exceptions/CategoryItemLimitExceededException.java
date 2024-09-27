package com.emazon.shopping_service.domain.exceptions;

public class CategoryItemLimitExceededException extends RuntimeException {
    public CategoryItemLimitExceededException(String message) {
        super(message);
    }
}
