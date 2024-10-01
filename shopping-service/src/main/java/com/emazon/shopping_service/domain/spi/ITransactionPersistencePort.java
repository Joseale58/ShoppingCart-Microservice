package com.emazon.shopping_service.domain.spi;

import java.time.LocalDateTime;

public interface ITransactionPersistencePort {
    LocalDateTime nextSupplyDate(Long articleId);
}
