package com.emazon.shopping_service.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Cart {

    private Long id;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<CartItem> cartItems;

    public Cart(Long id, String email, LocalDateTime createdDate, LocalDateTime updatedDate, List<CartItem> cartItems) {
        this.id = id;
        this.email = email;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.cartItems = cartItems;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
