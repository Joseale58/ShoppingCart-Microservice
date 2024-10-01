package com.emazon.shopping_service.application.handler;

import com.emazon.shopping_service.application.dto.AddProductRequest;

public interface ICartHandler {

    void addProductToCart(AddProductRequest addProductRequest, String email);
}
