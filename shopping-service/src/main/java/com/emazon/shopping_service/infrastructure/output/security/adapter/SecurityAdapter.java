package com.emazon.shopping_service.infrastructure.output.security.adapter;

import com.emazon.shopping_service.domain.spi.ISecurityPersitencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SecurityAdapter implements ISecurityPersitencePort {

    private static final ThreadLocal<String> currentToken = new ThreadLocal<>();

    @Override
    public void setToken(String jwtToken) {
        currentToken.set(jwtToken);
    }

    @Override
    public String getToken() {
        return currentToken.get();
    }

    @Override
    public void removeToken() {
        currentToken.remove();
    }
}
