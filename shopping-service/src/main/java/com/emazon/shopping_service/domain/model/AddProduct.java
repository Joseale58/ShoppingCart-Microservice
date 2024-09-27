package com.emazon.shopping_service.domain.model;

public class AddProduct {
    private Long productId;
    private Integer quantity;

    public AddProduct(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddProduct() {

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
}
