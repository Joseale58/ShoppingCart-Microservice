package com.emazon.shopping_service.utils;

public class Constants {
    private Constants() {
        throw new UnsupportedOperationException(UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    public static final String UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED = "Esta clase no deberías ser instanciada";

    //Messsages for exeptions
    public static final String EXCEPTION_MESSAGE = "Mensaje: ";

    //Usecases
    public static final String INSUFFICIENT_STOCK_EXCEPTION = "No hay suficiente stock para el producto solicitado, la próxima fecha de abastecimiento es: ";

    public static final int MAX_CATEGORIES_ALLOWED = 3;
    public static final String CATEGORY_ITEM_LIMIT_EXCEEDED_EXCEPTION = "No se pueden agregar más de 3 productos de una categoría";

    public static final String CART_DOES_NOT_EXISTS_EXCEPTION = "El carrito no existe";
    public static final String ITEM_HAS_NOT_BEEN_ADDED_TO_CART_EXCEPTION = "El producto no ha sido añadido al carrito";
    public static final String INSUFFICIENT_ADDED_ITEMS_TO_CART_EXCEPTION = "No se pueden restar más productos de los que hay en el carrito";
    public static final int MINIMUM_QUANTITY_AMOUNT = 0;
    //DTO
    public static final String PRODUCT_ID_CANNOT_BE_NULL = "El id del producto no puede ser nulo";
    public static final String PRODUCT_ID_MUST_BE_POSITIVE = "El id del producto debe ser positivo";
    public static final String PRODUCT_QUANTITY_CANNOT_BE_NULL = "La cantidad del producto no puede ser nula";
    public static final String PRODUCT_QUANTITY_MUST_BE_POSITIVE = "La cantidad del producto debe ser positiva";

    //Mapper
    public static final String SPRING_COMPONENT_MODEL = "spring";

    public static final int ONE_MONTH = 1;
}
