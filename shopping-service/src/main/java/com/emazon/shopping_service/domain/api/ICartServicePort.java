package com.emazon.shopping_service.domain.api;

import com.emazon.shopping_service.domain.model.*;
import com.emazon.shopping_service.domain.util.pageable.CustomPage;

import java.util.List;

public interface ICartServicePort {
    void addProductToCart(UpdateProduct updateProduct, String email);
    void checkStockAvailability(UpdateProduct updateProduct, Integer stock);
    void checkMaxCategories(List<Category> categories, Cart cart);
    void subtractProductFromCart(UpdateProduct updateProduct, String email);
    CartItems getCartItems(Integer page, Integer pageSize, String order, String sort, String brandName,String categoryName, String email);
    void updateCartItems(Product product, List<CartItem> cartItems);
    String calculateTotalPrice(List<CartItem> cartItems, CustomPage<Product> products);
}
