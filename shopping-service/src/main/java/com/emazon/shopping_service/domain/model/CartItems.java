package com.emazon.shopping_service.domain.model;

import com.emazon.shopping_service.domain.util.pageable.CustomPage;

public class CartItems {
    private CustomPage<Product> products;
    private String totalPrice;

    public CartItems(CustomPage<Product> products, String totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public CustomPage<Product> getProducts() {
        return products;
    }

    public void setProducts(CustomPage<Product> products) {
        this.products = products;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
