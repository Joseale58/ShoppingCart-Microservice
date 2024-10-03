package com.emazon.shopping_service.infrastructure.output.jpa.adapter;

import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.domain.model.CartItem;
import com.emazon.shopping_service.domain.model.UpdateProduct;
import com.emazon.shopping_service.domain.spi.ICartPersistencePort;
import com.emazon.shopping_service.infrastructure.exception.ItemDoesNotExistException;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartEntity;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartItemEntity;
import com.emazon.shopping_service.infrastructure.output.jpa.mapper.ICartEntityMapper;
import com.emazon.shopping_service.infrastructure.output.jpa.mapper.ICartItemEntityMapper;
import com.emazon.shopping_service.infrastructure.output.jpa.repository.ICartItemRepository;
import com.emazon.shopping_service.infrastructure.output.jpa.repository.ICartRepository;
import com.emazon.shopping_service.utils.Constants;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartJpaAdapter implements ICartPersistencePort {

    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final ICartEntityMapper cartEntityMapper;
    private final ICartItemEntityMapper cartItemEntityMapper;

    @Override
    public Cart createCart(String email) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCreatedDate(LocalDateTime.now());
        cartEntity.setUpdatedDate(LocalDateTime.now());
        cartEntity.setEmail(email);
        cartRepository.save(cartEntity);
        return cartEntityMapper.toCart(cartEntity);
    }

    @Override
    public Optional<Cart> getCartByUserEmail(String email) {
        Optional<CartEntity> cartEntity = cartRepository.getByEmail(email);
        return cartEntity.map(cartEntityMapper::toCart);
    }

    @Override
    public Optional<CartItem> getCartItemById(Long cartId, Long productId) {
        Optional<CartItemEntity> cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);
        return cartItem.map(cartItemEntityMapper::toCartItem);
    }

    @Override
    public void updateCart(Cart cart) {
        CartEntity cartEntity = cartEntityMapper.toCartEntity(cart);
        cartRepository.save(cartEntity);
    }

    @Override
    public void updateProductToCart(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemEntityMapper.toCartItemEntity(cartItem);
        cartItemRepository.save(cartItemEntity);
    }

    @Override
    public void deleteProductFromCart(CartItem cartItem) {
        Long id = cartItem.getId();
        Optional<CartItemEntity> entityInDbOpt = cartItemRepository.findById(id);
        if (entityInDbOpt.isPresent()) {
            cartItemRepository.deleteById(id);
        } else {
            throw new ItemDoesNotExistException(Constants.ITEM_HAS_NOT_BEEN_ADDED_TO_CART_EXCEPTION);
        }
    }

    @Override
    public List<CartItem> getCartItemsFromUserByEmail(String email) {
        Optional<List<CartItemEntity>> cartItems = cartItemRepository.findByCartEmail(email);
        if(cartItems.isPresent()){
            return cartItemEntityMapper.toCartItems(cartItems.get());
        }
        return List.of();
    }

}
