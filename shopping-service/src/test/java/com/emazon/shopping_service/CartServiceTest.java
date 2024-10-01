package com.emazon.shopping_service;

import com.emazon.shopping_service.domain.api.ICartServicePort;
import com.emazon.shopping_service.domain.exceptions.CategoryItemLimitExceededException;
import com.emazon.shopping_service.domain.exceptions.InsufficientStockException;
import com.emazon.shopping_service.domain.model.*;
import com.emazon.shopping_service.domain.spi.ICartPersistencePort;
import com.emazon.shopping_service.domain.spi.IStockPersistencePort;
import com.emazon.shopping_service.domain.spi.ITransactionPersistencePort;
import com.emazon.shopping_service.domain.usecase.CartUseCase;
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
    private AddProduct addProduct;

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
        addProduct = new AddProduct(product.getId(), 1);
    }

    @Test
    void shouldCreateNewCartIfCartDoesNotExist() {
        String email = "user@example.com";

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.empty());
        when(cartPersistencePort.createCart(email)).thenReturn(cart);
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.addProductToCart(addProduct, email);

        verify(cartPersistencePort).createCart(email);
        verify(cartPersistencePort).addProductToCart(any(CartItem.class));
    }

    @Test
    void shouldAddProductToExistingCart() {
        String email = "user@example.com";

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.empty());
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.addProductToCart(addProduct, email);

        verify(cartPersistencePort, never()).createCart(anyString());
        verify(cartPersistencePort).addProductToCart(any(CartItem.class));
    }

    @Test
    void shouldUpdateCartItemQuantityIfProductAlreadyInCart() {
        String email = "user@example.com";
        CartItem existingCartItem = new CartItem(1L, product.getId(), 2, cart);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.of(existingCartItem));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        addProduct.setQuantity(3);
        cartServicePort.addProductToCart(addProduct, email);

        assertEquals(5, existingCartItem.getQuantity());
        verify(cartPersistencePort).addProductToCart(existingCartItem);
    }

    @Test
    void shouldThrowInsufficientStockExceptionWhenStockIsInsufficient() {
        String email = "user@example.com";
        product.setStock(1);

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);
        when(transactionPersistencePort.nextSupplyDate(product.getId())).thenReturn(LocalDateTime.now().plusDays(2));

        addProduct.setQuantity(2);

        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () ->
                cartServicePort.addProductToCart(addProduct, email));

    }



    @Test
    void shouldUpdateCartUpdatedDate() {
        String email = "user@example.com";
        LocalDateTime beforeUpdate = cart.getUpdatedDate();

        when(cartPersistencePort.getCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartPersistencePort.getCartItemById(cart.getId(), product.getId())).thenReturn(Optional.empty());
        when(stockPersistencePort.getProductById(product.getId())).thenReturn(product);

        cartServicePort.addProductToCart(addProduct, email);

        assertTrue(cart.getUpdatedDate().isAfter(beforeUpdate));
        verify(cartPersistencePort).updateCart(cart);
    }

    @Test
    void shouldPassStockAvailabilityCheckWhenStockIsSufficient() {
        addProduct.setQuantity(5);
        int stock = 10;

        assertDoesNotThrow(() -> cartServicePort.checkStockAvailability(addProduct, stock));
    }

    @Test
    void shouldFailStockAvailabilityCheckWhenStockIsInsufficient() {
        addProduct.setQuantity(15);
        int stock = 10;

        when(transactionPersistencePort.nextSupplyDate(product.getId())).thenReturn(LocalDateTime.now().plusDays(2));

       assertThrows(InsufficientStockException.class, () ->
                cartServicePort.checkStockAvailability(addProduct, stock));
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
                cartServicePort.addProductToCart(addProduct, email));
    }
}
