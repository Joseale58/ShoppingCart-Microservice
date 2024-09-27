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
}
