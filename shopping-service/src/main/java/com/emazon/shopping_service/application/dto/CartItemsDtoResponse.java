package com.emazon.shopping_service.application.dto;

import com.emazon.shopping_service.domain.model.Product;
import com.emazon.shopping_service.domain.util.pageable.CustomPage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemsDtoResponse {
    private CustomPage<Product> products;
    private String totalPrice;


}
