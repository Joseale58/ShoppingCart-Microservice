package com.emazon.shopping_service.domain.spi;

import com.emazon.shopping_service.domain.model.Product;
import com.emazon.shopping_service.domain.util.pageable.CustomPage;

import java.util.List;

public interface IStockPersistencePort {
    Product getProductById(Long productId);
    CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort, String brandName, String categoryName, List<Long> productsId);

}
