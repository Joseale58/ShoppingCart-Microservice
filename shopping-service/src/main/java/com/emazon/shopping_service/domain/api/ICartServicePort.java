package com.emazon.shopping_service.domain.api;

import com.emazon.shopping_service.domain.model.AddProduct;
import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.domain.model.Category;

import java.util.List;

public interface ICartServicePort {
    void addProductToCart(AddProduct addProduct, Long userId);

    void checkStockAvailability(AddProduct addProduct, Integer stock);

    void checkMaxCategories(List<Category> categories, Cart cart);
}
