package com.emazon.shopping_service.infrastructure.output.security.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emazon.transaction_service.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtils {

    @Value("${JWT_SECRET}")
    private String privateKey;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        String jwt = JWT.create()
                .withSubject(username) //Añade el nombre de usuario
                .withClaim(SecurityConstants.CLAIM_ROLE, role) //Añade el rol del usuario
//                .withIssuedAt(new Date(System.currentTimeMillis())) //Añade la fecha de creación del token
//                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) //Añade la fecha de expiración del token
//                .withJWTId(UUID.randomUUID().toString()) //Añade un identificador único y random al token
                .sign(algorithm);

        return jwt;
    }

    public DecodedJWT validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(SecurityConstants.INVALID_TOKEN_MESSAGE);
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claim) {
        return decodedJWT.getClaim(claim);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
