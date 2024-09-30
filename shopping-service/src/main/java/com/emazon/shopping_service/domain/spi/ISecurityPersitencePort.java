package com.emazon.shopping_service.domain.spi;

public interface ISecurityPersitencePort {
    void setToken(String jwtToken);
    String getToken();
    void removeToken();
}
