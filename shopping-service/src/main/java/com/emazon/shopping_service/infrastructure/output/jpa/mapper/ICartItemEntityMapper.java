package com.emazon.shopping_service.infrastructure.output.jpa.mapper;

import com.emazon.shopping_service.domain.model.CartItem;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartItemEntity;
import com.emazon.shopping_service.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartItemEntityMapper {
    @Mapping(target = "productId", source = "productId")
    CartItem toCartItem(CartItemEntity cartItemEntity);

    @Mapping(target = "productId", source = "productId")
    CartItemEntity toCartItemEntity(CartItem cartItem);

    List<CartItem> toCartItems(List<CartItemEntity> cartItemEntityList);
}
