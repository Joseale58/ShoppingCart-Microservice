package com.emazon.shopping_service.utils;

public class Constants {
    private Constants() {
        throw new UnsupportedOperationException(UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    public static final String UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED = "Esta clase no deberías ser instanciada";

    //Messsages for exeptions
    public static final String EXCEPTION_MESSAGE = "Mensaje: ";

    //Usecases
    public static final String INSUFFICIENT_STOCK_EXCEPTION = "No hay suficiente stock para el producto solicitado";

    public static final int MAX_CATEGORIES_ALLOWED = 3;
    public static final String CATEGORY_ITEM_LIMIT_EXCEEDED_EXCEPTION = "No se pueden agregar más de 3 productos de una categoría";

    //DTO
    public static final String PRODUCT_ID_CANNOT_BE_NULL = "El id del producto no puede ser nulo";
    public static final String PRODUCT_ID_MUST_BE_POSITIVE = "El id del producto debe ser positivo";
    public static final String PRODUCT_QUANTITY_CANNOT_BE_NULL = "La cantidad del producto no puede ser nula";
    public static final String PRODUCT_QUANTITY_MUST_BE_POSITIVE = "La cantidad del producto debe ser positiva";

    //Mapper
    public static final String SPRING_COMPONENT_MODEL = "spring";
}
