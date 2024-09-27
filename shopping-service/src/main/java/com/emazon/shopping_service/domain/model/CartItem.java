package com.emazon.shopping_service.domain.model;

public class CartItem {

    private Long id;
    private Long productid;
    private Integer quantity;
    private Cart cart;

    public CartItem(Long id, Long productid, Integer quantity, Cart cart) {
        this.id = id;
        this.productid = productid;
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

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
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
