package com.emazon.shopping_service.infrastructure.input;

import com.emazon.shopping_service.application.dto.UpdateProductDtoRequest;
import com.emazon.shopping_service.application.handler.ICartHandler;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
@Validated
public class CartRestController {


    private final ICartHandler cartHandler;

    @Operation(
            summary = "Add product to cart",
            description = "This endpoint adds a specified quantity of an article based on availability of stock service to the user cart."
    )
    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(@RequestBody UpdateProductDtoRequest updateProductDtoRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getPrincipal().toString();
        cartHandler.addProductToCart(updateProductDtoRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Subtract product to cart",
            description = "This endpoint subtracts a specified quantity of an article based on the current amount of the item at the cart."
    )
    @PostMapping("/subtract")
    public ResponseEntity<String> subtractProductToCart(@RequestBody UpdateProductDtoRequest updateProductDtoRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getPrincipal().toString();
        cartHandler.subtractProductFromCart(updateProductDtoRequest, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
