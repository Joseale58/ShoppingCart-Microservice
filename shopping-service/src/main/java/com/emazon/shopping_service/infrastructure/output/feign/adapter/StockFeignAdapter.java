package com.emazon.shopping_service.infrastructure.output.feign.adapter;

import com.emazon.shopping_service.domain.model.Product;
import com.emazon.shopping_service.domain.spi.IStockPersistencePort;
import com.emazon.shopping_service.infrastructure.output.feign.client.IStockFeignClient;
import feign.FeignException;
import org.springframework.http.ResponseEntity;

public class StockFeignAdapter implements IStockPersistencePort {

    private final IStockFeignClient stockFeignClient;

    @Override
    public Product getProductById(Long productId) {
            ResponseEntity<Product> response = stockFeignClient.getProductById(productId);
            return response.getBody();
    }
}
