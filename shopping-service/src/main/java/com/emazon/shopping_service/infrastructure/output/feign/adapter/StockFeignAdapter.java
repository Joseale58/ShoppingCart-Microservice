package com.emazon.shopping_service.infrastructure.output.feign.adapter;

import com.emazon.shopping_service.domain.model.Product;
import com.emazon.shopping_service.domain.spi.IStockPersistencePort;
import com.emazon.shopping_service.domain.util.pageable.CustomPage;
import com.emazon.shopping_service.infrastructure.output.feign.client.IStockFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public class StockFeignAdapter implements IStockPersistencePort {

    private final IStockFeignClient stockFeignClient;

    @Override
    public Product getProductById(Long productId) {
            ResponseEntity<Product> response = stockFeignClient.getProductById(productId);
            return response.getBody();
    }

    @Override
    public CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort, String brandName, String categoryName, List<Long> productsId) {
            ResponseEntity<CustomPage<Product>> response = stockFeignClient.getPaginatedProducts(page, pageSize, order, sort, brandName, categoryName, productsId);
            return response.getBody();
    }
}
