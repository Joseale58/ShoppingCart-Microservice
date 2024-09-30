package com.emazon.shopping_service.infrastructure.output.jpa.adapter;

import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.domain.model.CartItem;
import com.emazon.shopping_service.domain.spi.ICartPersistenccePort;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartEntity;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartItemEntity;
import com.emazon.shopping_service.infrastructure.output.jpa.mapper.ICartEntityMapper;
import com.emazon.shopping_service.infrastructure.output.jpa.mapper.ICartItemEntityMapper;
import com.emazon.shopping_service.infrastructure.output.jpa.repository.ICartItemRepository;
import com.emazon.shopping_service.infrastructure.output.jpa.repository.ICartRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CartJpaAdapter implements ICartPersistenccePort {

    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final ICartEntityMapper cartEntityMapper;
    private final ICartItemEntityMapper cartItemEntityMapper;

    @Override
    public Cart createCart(Long userId) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserId(userId);
        cartRepository.save(cartEntity);
        return cartEntityMapper.toCart(cartEntity);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        Optional<CartEntity> cartEntity = cartRepository.getByUserId(userId);
        return cartEntity.map(cartEntityMapper::toCart);
    }

    @Override
    public Optional<CartItem> getCartItemById(Long cartId, Long productId) {
        Optional<CartItemEntity> cartItem = cartItemRepository.findByCartIdAndArticleId(cartId, productId);
        return cartItem.map(cartItemEntityMapper::toCartItem);
    }

    @Override
    public void updateCart(Cart cart) {
        CartEntity cartEntity = cartEntityMapper.toCartEntity(cart);
        cartRepository.save(cartEntity);
    }

    @Override
    public void addProductToCart(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemEntityMapper.toCartItemEntity(cartItem);
        cartItemRepository.save(cartItemEntity);
    }
}
