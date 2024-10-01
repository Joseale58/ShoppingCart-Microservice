package com.emazon.shopping_service.application.handler;

import com.emazon.shopping_service.application.dto.AddProductRequest;
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
    public void addProductToCart(AddProductRequest addProductRequest, String email) {
        cartServicePort.addProductToCart(productDtoMapper.toAddProduct(addProductRequest), email);
    }

}
