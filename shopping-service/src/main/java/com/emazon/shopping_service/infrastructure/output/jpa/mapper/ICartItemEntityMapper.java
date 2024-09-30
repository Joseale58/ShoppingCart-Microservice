package com.emazon.shopping_service.infrastructure.output.jpa.mapper;

import com.emazon.shopping_service.domain.model.CartItem;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartItemEntity;
import com.emazon.shopping_service.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartItemEntityMapper {
    CartItem toCartItem(CartItemEntity cartItemEntity);
    CartItemEntity toCartItemEntity(CartItem cartItem);
}
