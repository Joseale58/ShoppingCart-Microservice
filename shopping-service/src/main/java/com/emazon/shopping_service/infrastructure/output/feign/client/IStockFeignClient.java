package com.emazon.shopping_service.infrastructure.output.feign.client;

import com.emazon.shopping_service.domain.model.Product;
import com.emazon.shopping_service.domain.util.pageable.CustomPage;
import com.emazon.shopping_service.infrastructure.configuration.feign.FeignClientConfig;
import com.emazon.shopping_service.utils.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= FeignConstants.FEIGN_STOCK_API_NAME, url= FeignConstants.API_STOCK_URL, configuration = FeignClientConfig.class)
public interface IStockFeignClient {
    @GetMapping(FeignConstants.API_PATH_PRODUCT_CHECK_STOCK)
    ResponseEntity<Product> getProductById(@PathVariable(FeignConstants.PRODUCT_ID) Long productId);

    @GetMapping(FeignConstants.API_PATH_PRODUCTS_PAGINATED)
    ResponseEntity<CustomPage<Product>> getPaginatedProducts(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String order,
            @RequestParam String sort,
            @RequestParam String brandName,
            @RequestParam String categoryName,
            @RequestParam List<Long> productsId
    );
}
