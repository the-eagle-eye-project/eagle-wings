package com.theeagleeyeproject.eaglewings.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link JwtUtil} contains methods that assists the creation and consumption of a JWT token.
 *
 * @author johnmartinez
 */
@Data
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationHours;

    /**
     * Used to generate a new JWT for authentication purposes.
     *
     * @param userId UUID that belongs to a specific user.
     * @param claims custom attributes added to the JWT for processing.
     * @return a token
     */
    public String generateToken(String userId, Map<String, Object> claims) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDateTime = now.plusHours(expirationHours);

        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expiration = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Key signingKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        JwtBuilder jwt = Jwts.builder()
                .subject(userId)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(signingKey);

        for (Map.Entry<String, Object> claim : claims.entrySet()) {
            jwt.claim(claim.getKey(), claim.getValue());
        }

        return jwt.compact();
    }

    /**
     * Used to extract claims from the JWT.
     *
     * @param token input JWT
     * @return a {@link Map} of the claims
     */
    public Map<String, Object> extractClaims(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .build()
                .parseSignedClaims(token);

        Claims claims = claimsJws.getPayload();
        return new HashMap<>(claims);
    }

    /**
     * Used to validate if the token is still valid, expired or malformed.
     *
     * @param token input JWT
     * @return a boolean value
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            // Token is either malformed, expired, or signature validation failed
            return false;
        }
    }
}
