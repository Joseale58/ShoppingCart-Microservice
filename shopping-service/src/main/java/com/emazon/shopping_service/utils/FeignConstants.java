package com.emazon.shopping_service.utils;

public class FeignConstants {

    private FeignConstants() {
        throw new UnsupportedOperationException(Constants.UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    public static final String FEIGN_API_NAME = "PRODUCT-API";
    public static final String API_URL = "${STOCK_BASE_URL}";

    public static final String API_PATH_PRODUCT_CHECK_STOCK = "/products/{productId}";
    public static final String PRODUCT_ID = "productId";


    //Codes

    public static final int BAD_REQUEST_CODE = 400;
    public static final int FORBIDDEN_CODE = 403;
    public static final int NOT_FOUND_CODE = 404;
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;

    //Exceptions
    public static final String BAD_REQUEST = "Bad Request";
    public static final String FORBIDDEN = "Forbidden";
    public static final String NOT_FOUND = "Not Found";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error from remote service";



}
