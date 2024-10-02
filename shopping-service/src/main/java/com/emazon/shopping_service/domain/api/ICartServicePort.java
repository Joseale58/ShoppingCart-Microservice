package com.emazon.shopping_service.domain.api;

import com.emazon.shopping_service.domain.model.UpdateProduct;
import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.domain.model.Category;

import java.util.List;

public interface ICartServicePort {
    void addProductToCart(UpdateProduct updateProduct, String email);
    void checkStockAvailability(UpdateProduct updateProduct, Integer stock);
    void checkMaxCategories(List<Category> categories, Cart cart);
    void subtractProductFromCart(UpdateProduct updateProduct, String email);
}
