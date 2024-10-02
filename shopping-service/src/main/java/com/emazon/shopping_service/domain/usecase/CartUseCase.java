package com.emazon.shopping_service.domain.usecase;

import com.emazon.shopping_service.domain.api.ICartServicePort;
import com.emazon.shopping_service.domain.exceptions.*;
import com.emazon.shopping_service.domain.model.*;
import com.emazon.shopping_service.domain.spi.ICartPersistencePort;
import com.emazon.shopping_service.domain.spi.IStockPersistencePort;
import com.emazon.shopping_service.domain.spi.ITransactionPersistencePort;
import com.emazon.shopping_service.utils.Constants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartUseCase implements ICartServicePort {

    private final ICartPersistencePort cartPersistencePort;
    private final IStockPersistencePort stockPersistencePort;
    private final ITransactionPersistencePort transactionPersistencePort;

    public CartUseCase(ICartPersistencePort cartPersistencePort, IStockPersistencePort stockPersistencePort, ITransactionPersistencePort transactionPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
        this.stockPersistencePort = stockPersistencePort;
        this.transactionPersistencePort = transactionPersistencePort;
    }

    @Override
    public void addProductToCart(UpdateProduct updateProduct, String email) {

        Optional<Cart> optionalCart = cartPersistencePort.getCartByUserEmail(email);
        Cart cart;

        if (optionalCart.isEmpty()) {
            cart = cartPersistencePort.createCart(email);
        } else {
            cart = optionalCart.get();
        }

        Optional<CartItem> optionalCartItem = cartPersistencePort.getCartItemById(cart.getId(), updateProduct.getProductId());
        CartItem cartItem;

        if(optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + updateProduct.getQuantity());
            updateProduct.setQuantity(cartItem.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductid(updateProduct.getProductId());
            cartItem.setQuantity(updateProduct.getQuantity());
        }

        Product product = stockPersistencePort.getProductById(updateProduct.getProductId());

        this.checkStockAvailability(updateProduct,product.getStock());

        this.checkMaxCategories(product.getCategories(), cart);

        cart.setUpdatedDate(LocalDateTime.now());
        cartPersistencePort.updateCart(cart);
        cartPersistencePort.updateProductToCart(cartItem);

    }

    @Override
    public void checkStockAvailability(UpdateProduct updateProduct, Integer stock) {
        if(stock < updateProduct.getQuantity()) {
            LocalDateTime nextSupplyDate = transactionPersistencePort.nextSupplyDate(updateProduct.getProductId());
            throw new InsufficientStockException(Constants.INSUFFICIENT_STOCK_EXCEPTION + nextSupplyDate);
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

    @Override
    public void subtractProductFromCart(UpdateProduct updateProduct, String email) {

        Optional<Cart> optionalCart = cartPersistencePort.getCartByUserEmail(email);
        Cart cart;

        if (optionalCart.isEmpty()) {
            throw new CartDoesNotExistsException(Constants.CART_DOES_NOT_EXISTS_EXCEPTION);
        } else {
            cart = optionalCart.get();
        }

        Optional<CartItem> optionalCartItem = cartPersistencePort.getCartItemById(cart.getId(), updateProduct.getProductId());
        CartItem cartItem;

        if(optionalCartItem.isEmpty()) {
            throw new ItemHasNotBeenAddedToCartException(Constants.ITEM_HAS_NOT_BEEN_ADDED_TO_CART_EXCEPTION);
        }


        cartItem = optionalCartItem.get();

        if(cartItem.getQuantity() < updateProduct.getQuantity()) {
            throw new InsufficientAddedItemsToCartException(Constants.INSUFFICIENT_ADDED_ITEMS_TO_CART_EXCEPTION);
        }

        cartItem.setQuantity(cartItem.getQuantity() - updateProduct.getQuantity());
        updateProduct.setQuantity(cartItem.getQuantity());

        Product product = stockPersistencePort.getProductById(updateProduct.getProductId());

        if(updateProduct.getQuantity()<=Constants.MINIMUM_QUANTITY_AMOUNT){
            cartPersistencePort.deleteProductFromCart(cartItem);
        } else {
            this.checkStockAvailability(updateProduct,product.getStock());

            cart.setUpdatedDate(LocalDateTime.now());
            cartPersistencePort.updateCart(cart);
            cartPersistencePort.updateProductToCart(cartItem);
        }



    }
}
