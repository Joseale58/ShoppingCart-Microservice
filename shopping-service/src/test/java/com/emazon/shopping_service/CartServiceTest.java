package com.emazon.shopping_service;

import com.emazon.shopping_service.domain.api.ICartServicePort;
import com.emazon.shopping_service.domain.exceptions.*;
import com.emazon.shopping_service.domain.model.*;
import com.emazon.shopping_service.domain.spi.ICartPersistencePort;
import com.emazon.shopping_service.domain.spi.IStockPersistencePort;
import com.emazon.shopping_service.domain.spi.ITransactionPersistencePort;
import com.emazon.shopping_service.domain.usecase.CartUseCase;
import com.emazon.shopping_service.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {
    private ICartPersistencePort cartPersistencePort;
    private IStockPersistencePort stockPersistencePort;
    private ITransactionPersistencePort transactionPersistencePort;
    private ICartServicePort cartServicePort;

    private Cart cart;
    private CartItem cartItem;
    private Product product;
    private UpdateProduct updateProduct;

    @BeforeEach
    public void setUp() {
        cartPersistencePort = Mockito.mock(ICartPersistencePort.class);
        stockPersistencePort = Mockito.mock(IStockPersistencePort.class);
        transactionPersistencePort = Mockito.mock(ITransactionPersistencePort.class);
        cartServicePort = new CartUseCase(cartPersistencePort, stockPersistencePort, transactionPersistencePort);

        List<Category> categoryList = Arrays.asList(
                new Category(1L, "Category 1", "Description 1"),
                new Category(2L, "Category 2", "Description 2")
        );

        product = new Product(1L, "Valid Product", "Valid Description", 100, 10.0, new Brand(1L, "Brand", "Brand Description"), categoryList);
        cart = new Cart(1L, "user@example.com", LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
        cartItem = new CartItem(1L, product.getId(), 1, cart);
        updateProduct = new UpdateProduct(product.getId(), 1);
    }

    @Test
    void shouldCreateNewCartIfCartDoesNotExist() {
        String email = "user@example.com";

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.empty());
        when(cartPersistencePort.createCart(email)).thenReturn(cart);
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.addProductToCart(updateProduct, email);

        verify(cartPersistencePort).createCart(email);
        verify(cartPersistencePort).updateProductToCart(any(CartItem.class));
    }

    @Test
    void shouldAddProductToExistingCart() {
        String email = "user@example.com";

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.empty());
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.addProductToCart(updateProduct, email);

        verify(cartPersistencePort, never()).createCart(anyString());
        verify(cartPersistencePort).updateProductToCart(any(CartItem.class));
    }

    @Test
    void shouldUpdateCartItemQuantityIfProductAlreadyInCart() {
        String email = "user@example.com";
        CartItem existingCartItem = new CartItem(1L, product.getId(), 2, cart);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.of(existingCartItem));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        updateProduct.setQuantity(3);
        cartServicePort.addProductToCart(updateProduct, email);

        assertEquals(5, existingCartItem.getQuantity());
        verify(cartPersistencePort).updateProductToCart(existingCartItem);
    }

    @Test
    void shouldThrowInsufficientStockExceptionWhenStockIsInsufficient() {
        String email = "user@example.com";
        product.setStock(1);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);
        when(transactionPersistencePort.nextSupplyDate(product.getId())).thenReturn(LocalDateTime.now().plusDays(2));

        updateProduct.setQuantity(2);

        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () ->
                cartServicePort.addProductToCart(updateProduct, email));

    }



    @Test
    void shouldUpdateCartUpdatedDate() {
        String email = "user@example.com";
        LocalDateTime beforeUpdate = cart.getUpdatedDate();

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.empty());
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.addProductToCart(updateProduct, email);

        assertTrue(cart.getUpdatedDate().isAfter(beforeUpdate));
        verify(cartPersistencePort).updateCart(cart);
    }

    @Test
    void shouldPassStockAvailabilityCheckWhenStockIsSufficient() {
        updateProduct.setQuantity(5);
        int stock = 10;

        assertDoesNotThrow(() -> cartServicePort.checkStockAvailability(updateProduct, stock));
    }

    @Test
    void shouldFailStockAvailabilityCheckWhenStockIsInsufficient() {
        updateProduct.setQuantity(15);
        int stock = 10;

        when(transactionPersistencePort.nextSupplyDate(product.getId())).thenReturn(LocalDateTime.now().plusDays(2));

       assertThrows(InsufficientStockException.class, () ->
                cartServicePort.checkStockAvailability(updateProduct, stock));
    }

    @Test
    void shouldPassCategoryLimitCheckWhenUnderLimit() {
        List<Category> newProductCategories = Collections.singletonList(new Category(3L, "Category 3", "Description"));

        cart.setCartItems(Arrays.asList(
                new CartItem(1L, 1L, 1, cart),
                new CartItem(2L, 2L, 1, cart)
        ));

        when(stockPersistencePort.getProductById(1L)).thenReturn(new Product(1L, "Product 1", "Desc", 10, 10.0, null, Collections.singletonList(new Category(1L, "Category 1", "Desc"))));
        when(stockPersistencePort.getProductById(2L)).thenReturn(new Product(2L, "Product 2", "Desc", 10, 10.0, null, Collections.singletonList(new Category(2L, "Category 2", "Desc"))));

        assertDoesNotThrow(() -> cartServicePort.checkMaxCategories(newProductCategories, cart));
    }


    @Test
    void shouldThrowCategoryItemLimitExceededExceptionWhenMaxItemsPerCategoryExceeded() {
        String email = "user@example.com";
        Category category1 = new Category(1L, "Category 1", "Description");
        List<Category> categoryList = Collections.singletonList(category1);

        cart.setCartItems(Arrays.asList(
                new CartItem(1L, 1L, 1, cart),
                new CartItem(2L, 2L, 1, cart),
                new CartItem(3L, 3L, 1, cart)
        ));

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.empty());

        when(stockPersistencePort.getProductById(1L)).thenReturn(
                new Product(1L, "Product 1", "Desc", 10, 10.0, null, categoryList));
        when(stockPersistencePort.getProductById(2L)).thenReturn(
                new Product(2L, "Product 2", "Desc", 10, 10.0, null, categoryList));
        when(stockPersistencePort.getProductById(3L)).thenReturn(
                new Product(3L, "Product 3", "Desc", 10, 10.0, null, categoryList));

        product.setCategories(categoryList);
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        assertThrows(CategoryItemLimitExceededException.class, () ->
                cartServicePort.addProductToCart(updateProduct, email));
    }

    @Test
    void shouldThrowInsufficientAddedItemsToCartExceptionWhenQuantityToSubtractExceedsCartQuantity() {
        String email = "user@example.com";
        UpdateProduct subtractProduct = new UpdateProduct(product.getId(), 10); // Intentar restar 10, pero solo hay 5

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.of(cartItem));

        InsufficientAddedItemsToCartException exception = assertThrows(InsufficientAddedItemsToCartException.class, () ->
                cartServicePort.subtractProductFromCart(subtractProduct, email));

        assertEquals(Constants.INSUFFICIENT_ADDED_ITEMS_TO_CART_EXCEPTION, exception.getMessage());
        verify(cartPersistencePort, never()).updateCart(any());
        verify(cartPersistencePort, never()).updateProductToCart(any());
    }

    @Test
    void shouldSubtractProductFromExistingCartSuccessfully() {
        String email = "user@example.com";
        cartItem.setQuantity(2);
        UpdateProduct subtractProduct = new UpdateProduct(product.getId(), 1);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.of(cartItem));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        LocalDateTime beforeUpdate = cart.getUpdatedDate();

        cartServicePort.subtractProductFromCart(subtractProduct, email);

        assertEquals(1, cartItem.getQuantity());
        assertTrue(cart.getUpdatedDate().isAfter(beforeUpdate));
        verify(cartPersistencePort).updateCart(cart);
        verify(cartPersistencePort).updateProductToCart(cartItem);
    }

    @Test
    void shouldThrowCartDoesNotExistsExceptionWhenCartDoesNotExist() {
        String email = "nonexistent@example.com";
        UpdateProduct subtractProduct = new UpdateProduct(product.getId(), 1);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.empty());

        CartDoesNotExistsException exception = assertThrows(CartDoesNotExistsException.class, () ->
                cartServicePort.subtractProductFromCart(subtractProduct, email));

        assertEquals(Constants.CART_DOES_NOT_EXISTS_EXCEPTION, exception.getMessage());
        verify(cartPersistencePort, never()).updateCart(any());
        verify(cartPersistencePort, never()).updateProductToCart(any());
    }

    @Test
    void shouldThrowItemHasNotBeenAddedToCartExceptionWhenItemNotInCart() {
        String email = "user@example.com";
        UpdateProduct subtractProduct = new UpdateProduct(product.getId(), 1);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.empty());

        ItemHasNotBeenAddedToCartException exception = assertThrows(ItemHasNotBeenAddedToCartException.class, () ->
                cartServicePort.subtractProductFromCart(subtractProduct, email));

        assertEquals(Constants.ITEM_HAS_NOT_BEEN_ADDED_TO_CART_EXCEPTION, exception.getMessage());
        verify(cartPersistencePort, never()).updateCart(any());
        verify(cartPersistencePort, never()).updateProductToCart(any());
    }

    @Test
    void shouldDeleteProductFromCartWhenResultingQuantityIsZero() {
        String email = "user@example.com";
        cartItem.setQuantity(5);
        UpdateProduct subtractProduct = new UpdateProduct(product.getId(), 5);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.of(cartItem));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.subtractProductFromCart(subtractProduct, email);

        assertEquals(0, cartItem.getQuantity());
        verify(cartPersistencePort).deleteProductFromCart(cartItem);
        verify(cartPersistencePort, never()).updateProductToCart(any());
    }

    @Test
    void shouldDeleteProductFromCartWhenResultingQuantityIsBelowMinimum() {
        String email = "user@example.com";
        cartItem.setQuantity(4);
        UpdateProduct subtractProduct = new UpdateProduct(product.getId(), 5);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.of(cartItem));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        assertThrows(InsufficientAddedItemsToCartException.class, () ->
                cartServicePort.subtractProductFromCart(subtractProduct, email));

        verify(cartPersistencePort, never()).updateProductToCart(any());
    }





}
