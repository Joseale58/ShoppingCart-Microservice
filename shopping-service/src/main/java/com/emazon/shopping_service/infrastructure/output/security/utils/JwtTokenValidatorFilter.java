package com.emazon.shopping_service.infrastructure.output.security.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.emazon.transaction_service.utils.SecurityConstants;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;

    public JwtTokenValidatorFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,@Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(jwtToken != null){
            jwtToken = jwtToken.substring(SecurityConstants.CHARACTER_NUMBER_SEVEN);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            String username = jwtUtils.extractUsername(decodedJWT);
            String role = decodedJWT.getClaim(SecurityConstants.CLAIM_ROLE).asString();


            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        }
        filterChain.doFilter(request, response);
    }


}
