package com.emazon.shopping_service.domain.spi;

import com.emazon.shopping_service.domain.model.Product;

public interface IStockPersistencePort {
    Product getProductById(Long productId);

}
