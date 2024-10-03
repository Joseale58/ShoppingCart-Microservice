package com.emazon.shopping_service.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Product {

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Integer cartQuantity;
    private LocalDateTime nextSupplyAvailability;
    private Double price;
    private Brand brand;
    private List<Category> categories;

    public Product(){}

    public Product(Long id, String name, String description, Integer stock, Double price, Brand brand, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.brand = brand;
        this.categories = categories;
    }

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public LocalDateTime getNextSupplyAvailability() {
        return nextSupplyAvailability;
    }

    public void setNextSupplyAvailability(LocalDateTime nextSupplyAvailability) {
        this.nextSupplyAvailability = nextSupplyAvailability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
