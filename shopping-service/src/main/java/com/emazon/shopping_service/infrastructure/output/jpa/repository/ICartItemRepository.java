package com.emazon.shopping_service.infrastructure.output.jpa.repository;

import com.emazon.shopping_service.domain.model.CartItem;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
}
