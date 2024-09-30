package com.emazon.shopping_service.infrastructure.configuration;


import com.emazon.shopping_service.domain.api.ICartServicePort;
import com.emazon.shopping_service.domain.spi.ICartPersistenccePort;
import com.emazon.shopping_service.domain.usecase.CartUseCase;
import com.emazon.shopping_service.infrastructure.output.jpa.adapter.CartJpaAdapter;
import com.emazon.shopping_service.infrastructure.output.jpa.mapper.ICartEntityMapper;
import com.emazon.shopping_service.infrastructure.output.jpa.mapper.ICartItemEntityMapper;
import com.emazon.shopping_service.infrastructure.output.jpa.repository.ICartItemRepository;
import com.emazon.shopping_service.infrastructure.output.jpa.repository.ICartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICartItemRepository cartItemRepository;
    private final ICartRepository cartRepository;
    private final ICartItemEntityMapper cartItemEntityMapper;
    private final ICartEntityMapper cartEntityMapper;

    @Bean
    public ICartPersistenccePort cartPersistenccePort(){
        return new CartJpaAdapter(cartRepository, cartItemRepository, cartEntityMapper, cartItemEntityMapper);
    }



    @Bean
    public ICartServicePort cartServicePort() {
        return new CartUseCase()
    }

}