package com.emazon.shopping_service.infrastructure.configuration.feign;

import com.emazon.shopping_service.infrastructure.output.security.adapter.SecurityAdapter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final SecurityAdapter securityAdapter;

    @Override
    public void apply(RequestTemplate template) {

        String token = securityAdapter.getToken();

        if (token != null && !token.isEmpty()) {
            template.header("Authorization", token);
        }

        if ("PATCH".equalsIgnoreCase(template.method())) {
            template.header("X-HTTP-Method-Override", "PATCH");
        }

    }
}
