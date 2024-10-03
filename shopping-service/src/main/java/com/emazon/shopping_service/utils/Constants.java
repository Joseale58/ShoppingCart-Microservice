package com.emazon.shopping_service.utils;

public class Constants {
    private Constants() {
        throw new UnsupportedOperationException(UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    public static final String UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED = "Esta clase no deberías ser instanciada";


    //For Rest Controller
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "5";
    public static final String DEFAULT_SORT_DIRECTION = "ASC";
    public static final String DEFAULT_SORT_BY = "name";
    public static final String DEFAULT_CATEGORY_NAME = "";
    public static final String DEFAULT_BRAND_NAME = "";


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


    public static final int PAGE_MINIMUM_INDEX = 0;
    public static final String PAGE_MUST_BE_POSITIVE_EXCEPTION = "El número de página debe ser minimamente 0";
    public static final int PAGE_MIN_SIZE_ILLEGAL = 0;
    public static final int PAGE_MAX_SIZE = 100;
    public static final String PAGE_SIZE_RANGE_EXCEPTION = "El tamaño de la página debe estar entre 0 y 100";
    public static final String PAGE_ASC_OPTION = "ASC";
    public static final String PAGE_DESC_OPTION = "DESC";
    public static final String PAGE_SORT_DIRECTION_EXCEPTION = "La dirección de ordenamiento debe ser 'ASC' o 'DESC'";
    public static final String EMPTY_STRING = "";

    public static final String CART_ITEM_NOT_FOUND_EXCEPTION = "El producto no se encuentra en el carrito";
    public static final double INITIAL_TOTAL = 0.0;
    //DTO
    public static final String PRODUCT_ID_CANNOT_BE_NULL = "El id del producto no puede ser nulo";
    public static final String PRODUCT_ID_MUST_BE_POSITIVE = "El id del producto debe ser positivo";
    public static final String PRODUCT_QUANTITY_CANNOT_BE_NULL = "La cantidad del producto no puede ser nula";
    public static final String PRODUCT_QUANTITY_MUST_BE_POSITIVE = "La cantidad del producto debe ser positiva";

    //Mapper
    public static final String SPRING_COMPONENT_MODEL = "spring";

    public static final int ONE_MONTH = 1;
}
