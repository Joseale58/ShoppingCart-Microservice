package com.emazon.shopping_service.infrastructure.output.jpa.mapper;


import com.emazon.shopping_service.domain.model.Cart;
import com.emazon.shopping_service.infrastructure.output.jpa.entity.CartEntity;
import com.emazon.shopping_service.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartEntityMapper {


    @Mapping(source = "email", target = "email")
    Cart toCart(CartEntity cartEntity);

    @Mapping(source = "email", target = "email")
    CartEntity toCartEntity(Cart cart);
}
