package com.miniproject.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private static final String Issuer = "ENVEU INC";

    public String generateJwtToken(String email) {
        Map<String, String> claims = new HashMap<>();
        claims.put("id", "1");
        return JWT.create().withIssuer(Issuer)
                .withIssuedAt(new Date()).withSubject(email)
                .withClaim("userDetails", claims).withExpiresAt(new Date(System.currentTimeMillis() + 30000000))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public boolean isVerified(String token) {
        if(token == null)
            return false;
        JWTVerifier verifier = getJWTVerifier();
        Date expirationDate = verifier.verify(token).getExpiresAt();
        return !expirationDate.before(new Date());
    }

    public String getSubject(String token){
        if(token == null)
            return null;
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    public Map<String, Claim> getClaim(String token){
        if(token == null)
            return null;
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaims();
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(Issuer).build();
        }catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token can not be verified");
        }
        return verifier;
    }
}
