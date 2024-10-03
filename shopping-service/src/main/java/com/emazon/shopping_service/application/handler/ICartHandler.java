package com.emazon.shopping_service.application.handler;

import com.emazon.shopping_service.application.dto.CartItemsDtoResponse;
import com.emazon.shopping_service.application.dto.UpdateProductDtoRequest;
import com.emazon.shopping_service.domain.model.CartItems;

public interface ICartHandler {

    void addProductToCart(UpdateProductDtoRequest updateProductDtoRequest, String email);
    void subtractProductFromCart(UpdateProductDtoRequest updateProductDtoRequest, String email);
    CartItemsDtoResponse getCartItems(Integer page, Integer pageSize, String order, String sort, String brandName, String categoryName, String email);
}
