package com.emazon.shopping_service.application.dto;


import com.emazon.shopping_service.utils.Constants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddProductRequest {

    @NotNull(message = Constants.PRODUCT_ID_CANNOT_BE_NULL)
    @Positive(message = Constants.PRODUCT_ID_MUST_BE_POSITIVE)
    private Long productId;

    @NotNull(message = Constants.PRODUCT_QUANTITY_CANNOT_BE_NULL)
    @Positive(message = Constants.PRODUCT_QUANTITY_MUST_BE_POSITIVE)
    private Integer quantity;

    public AddProductRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public @NotNull(message = Constants.PRODUCT_ID_CANNOT_BE_NULL) @Positive(message = Constants.PRODUCT_ID_MUST_BE_POSITIVE) Long getProductId() {
        return productId;
    }

    public void setProductId(@NotNull(message = Constants.PRODUCT_ID_CANNOT_BE_NULL) @Positive(message = Constants.PRODUCT_ID_MUST_BE_POSITIVE) Long productId) {
        this.productId = productId;
    }

    public @NotNull(message = Constants.PRODUCT_QUANTITY_CANNOT_BE_NULL) @Positive(message = Constants.PRODUCT_QUANTITY_MUST_BE_POSITIVE) Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull(message = Constants.PRODUCT_QUANTITY_CANNOT_BE_NULL) @Positive(message = Constants.PRODUCT_QUANTITY_MUST_BE_POSITIVE) Integer quantity) {
        this.quantity = quantity;
    }
}
