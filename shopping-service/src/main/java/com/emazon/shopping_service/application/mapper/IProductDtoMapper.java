package com.emazon.shopping_service.application.mapper;

import com.emazon.shopping_service.application.dto.AddProductRequest;
import com.emazon.shopping_service.domain.model.AddProduct;
import com.emazon.shopping_service.domain.model.Product;
import com.emazon.shopping_service.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductDtoMapper {
    AddProduct toAddProduct(AddProductRequest addProductRequest);
}
