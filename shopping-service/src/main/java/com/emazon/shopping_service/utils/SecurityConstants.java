package com.emazon.shopping_service.utils;

public class SecurityConstants {

    private SecurityConstants() {
        throw new UnsupportedOperationException(Constants.UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    //Token
    public static final Integer CHARACTER_NUMBER_SEVEN = 7;

    //Claims
    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_ROLE_ADMIN = "admin";
    public static final String CLAIM_ROLE_AUX_BODEGA = "aux_bodega";

    //Exceptions
    public static final String INVALID_TOKEN_MESSAGE = "Token invalido, no autorizado";


}
