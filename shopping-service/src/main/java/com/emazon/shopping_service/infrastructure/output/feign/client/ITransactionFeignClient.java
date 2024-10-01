package com.emazon.shopping_service.infrastructure.output.feign.client;


import com.emazon.shopping_service.infrastructure.configuration.feign.FeignClientConfig;
import com.emazon.shopping_service.utils.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@FeignClient(name =FeignConstants.FEIGN_TRANSACTION_NAME, url =FeignConstants.API_TRANSACTION_URL,configuration = FeignClientConfig.class)
public interface ITransactionFeignClient {
    @GetMapping(FeignConstants.API_PATH_LAST_DATE_SUPPLY)
    LocalDateTime lastSupplyDate(@PathVariable(FeignConstants.PRODUCT_ID) Long productId);
}
