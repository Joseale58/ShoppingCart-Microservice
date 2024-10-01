package com.emazon.shopping_service.domain.spi;

import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.domain.model.CartItem;

import java.util.Optional;

public interface ICartPersistencePort {
    Cart createCart(String email);
    Optional<Cart> getCartByUserEmail(String email);
    Optional<CartItem> getCartItemById(Long cartId, Long productId);
    void updateCart(Cart cart);
    void addProductToCart(CartItem cartItem);
}
