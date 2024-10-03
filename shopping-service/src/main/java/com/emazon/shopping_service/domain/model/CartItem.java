package com.emazon.shopping_service.domain.model;

public class CartItem {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Cart cart;

    public CartItem(Long id, Long productId, Integer quantity, Cart cart) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.cart = cart;
    }

    public CartItem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
