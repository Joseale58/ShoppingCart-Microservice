package com.emazon.shopping_service.infrastructure.exceptionhandler;

import com.emazon.shopping_service.domain.exceptions.CategoryItemLimitExceededException;
import com.emazon.shopping_service.domain.exceptions.InsufficientStockException;
import com.emazon.shopping_service.infrastructure.output.feign.exceptions.BadRequestException;
import com.emazon.shopping_service.infrastructure.output.feign.exceptions.NotFoundException;
import com.emazon.shopping_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = Constants.EXCEPTION_MESSAGE;

    @ExceptionHandler(CategoryItemLimitExceededException.class)
    public ResponseEntity<Map<String, String>> handleCategoryItemLimitExceededException(
            CategoryItemLimitExceededException categoryItemLimitExceededException) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of(MESSAGE, categoryItemLimitExceededException.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientStockException(
            InsufficientStockException insufficientStockException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(MESSAGE, insufficientStockException.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(
            BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(MESSAGE, badRequestException.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(
            NotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(MESSAGE, notFoundException.getMessage()));
    }

}