package com.emazon.shopping_service.domain.usecase;

import com.emazon.shopping_service.domain.api.ICartServicePort;
import com.emazon.shopping_service.domain.exceptions.CategoryItemLimitExceededException;
import com.emazon.shopping_service.domain.exceptions.InsufficientStockException;
import com.emazon.shopping_service.domain.model.*;
import com.emazon.shopping_service.domain.spi.ICartPersistenccePort;
import com.emazon.shopping_service.domain.spi.IStockPersistencePort;
import com.emazon.shopping_service.utils.Constants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartUseCase implements ICartServicePort {

    private final ICartPersistenccePort cartPersistencePort;
    private final IStockPersistencePort stockPersistencePort;

    public CartUseCase(ICartPersistenccePort cartPersistencePort, IStockPersistencePort stockPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
        this.stockPersistencePort = stockPersistencePort;
    }

    @Override
    public void addProductToCart(AddProduct addProduct, Long userId) {

        Optional<Cart> optionalCart = cartPersistencePort.getCartByUserId(userId);
        Cart cart;

        if (optionalCart.isEmpty()) {
            cart = cartPersistencePort.createCart(userId);
        } else {
            cart = optionalCart.get();
        }

        Optional<CartItem> optionalCartItem = cartPersistencePort.getCartItemById(cart.getId(), addProduct.getProductId());
        CartItem cartItem = new CartItem();

        if(optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + addProduct.getQuantity());
            addProduct.setQuantity(cartItem.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductid(addProduct.getProductId());
            cartItem.setQuantity(addProduct.getQuantity());
        }

        Product product = stockPersistencePort.getProductById(addProduct.getProductId());

        this.checkStockAvailability(addProduct,product.getStock());

        this.checkMaxCategories(product.getCategories(), cart);

        cart.setUpdatedDate(LocalDateTime.now());
        cartPersistencePort.updateCart(cart);
        cartPersistencePort.addProductToCart(cartItem);

    }

    @Override
    public void checkStockAvailability(AddProduct addProduct, Integer stock) {
        if(stock < addProduct.getQuantity()) {
            throw new InsufficientStockException(Constants.INSUFFICIENT_STOCK_EXCEPTION);
        }
    }

    @Override
    public void checkMaxCategories(List<Category> categories, Cart cart) {

        Map<Category, Integer> quantityPerCategory = new HashMap<>();

        if(cart.getCartItems() == null) {
            return;
        }

        for(CartItem cartItem : cart.getCartItems()) {
            Product existingProduct = stockPersistencePort.getProductById(cartItem.getProductid());
            for (Category category : existingProduct.getCategories()) {
                quantityPerCategory.put(category, quantityPerCategory.getOrDefault(category, 0) + 1);
            }
        }

        for (Category category : categories) {
            if((quantityPerCategory.getOrDefault(category, 0)+1) > Constants.MAX_CATEGORIES_ALLOWED) {
                throw new CategoryItemLimitExceededException(Constants.CATEGORY_ITEM_LIMIT_EXCEEDED_EXCEPTION);
            }
        }


    }
}
