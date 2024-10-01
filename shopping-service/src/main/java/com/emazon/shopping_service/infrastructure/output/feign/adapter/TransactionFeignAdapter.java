package com.emazon.shopping_service.infrastructure.output.feign.adapter;

import com.emazon.shopping_service.domain.spi.ITransactionPersistencePort;
import com.emazon.shopping_service.infrastructure.output.feign.client.ITransactionFeignClient;
import com.emazon.shopping_service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TransactionFeignAdapter implements ITransactionPersistencePort {

    private final ITransactionFeignClient transactionFeignClient;

    @Override
    public LocalDateTime nextSupplyDate(Long articleId) {
        LocalDateTime lastSupplyDate = transactionFeignClient.lastSupplyDate(articleId);
        return lastSupplyDate.plusMonths(Constants.ONE_MONTH);
    }


}
