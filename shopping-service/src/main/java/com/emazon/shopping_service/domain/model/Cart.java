package com.emazon.shopping_service.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Cart {

    private Long id;
    private Long userId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<CartItem> cartItems;



    public Cart(Long id, Long userId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userId = userId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Cart() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
