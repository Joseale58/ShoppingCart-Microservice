package com.emazon.shopping_service.domain.spi;

import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.domain.model.CartItem;

import java.util.Optional;

public interface ICartPersistenccePort {
    Cart createCart(Long userId);
    Optional<Cart> getCartByUserId(Long userId);
    Optional<CartItem> getCartItemById(Long cartId, Long productId);
    void updateCart(Cart cart);
    void addProductToCart(Long productId, Long userId);
}
