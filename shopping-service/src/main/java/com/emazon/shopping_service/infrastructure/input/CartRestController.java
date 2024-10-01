package com.emazon.shopping_service.infrastructure.input;

import com.emazon.shopping_service.application.dto.AddProductRequest;
import com.emazon.shopping_service.application.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
@Validated
public class CartRestController {


    private final ICartHandler cartHandler;

    @PostMapping
    public ResponseEntity<String> addProductToCart(@RequestBody AddProductRequest addProductRequest) {
        cartHandler.addProductToCart(addProductRequest, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
