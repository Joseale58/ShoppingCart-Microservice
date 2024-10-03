package com.emazon.shopping_service.application.handler;

import com.emazon.shopping_service.application.dto.CartItemsDtoResponse;
import com.emazon.shopping_service.application.dto.UpdateProductDtoRequest;
import com.emazon.shopping_service.application.mapper.IProductDtoMapper;
import com.emazon.shopping_service.domain.api.ICartServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartHandler implements ICartHandler{

    private final ICartServicePort cartServicePort;
    private final IProductDtoMapper productDtoMapper;

    @Override
    public void addProductToCart(UpdateProductDtoRequest updateProductDtoRequest, String email) {
        cartServicePort.addProductToCart(productDtoMapper.toUpdateProduct(updateProductDtoRequest), email);
    }

    @Override
    public void subtractProductFromCart(UpdateProductDtoRequest updateProductDtoRequest, String email) {
        cartServicePort.subtractProductFromCart(productDtoMapper.toUpdateProduct(updateProductDtoRequest), email);
    }

    @Override
    public CartItemsDtoResponse getCartItems(Integer page, Integer pageSize, String order, String sort, String brandName,String categoryName,  String email) {
        return productDtoMapper.toCartItemsDtoResponse(cartServicePort.getCartItems(page, pageSize, order, sort, brandName, categoryName, email));
    }

}
