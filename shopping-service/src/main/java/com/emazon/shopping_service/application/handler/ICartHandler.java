package com.emazon.shopping_service.application.handler;

import com.emazon.shopping_service.application.dto.UpdateProductDtoRequest;

public interface ICartHandler {

    void addProductToCart(UpdateProductDtoRequest updateProductDtoRequest, String email);
    void subtractProductFromCart(UpdateProductDtoRequest updateProductDtoRequest, String email);
}
