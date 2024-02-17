package com.theeagleeyeproject.eaglewings.utility;

import com.theeagleeyeproject.eaglewings.security.Role;
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
     * @param role   account's role ENUM that is used to be added to the JWT
     * @return a token
     */
    public String generateToken(String userId, Role role) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDateTime = now.plusHours(expirationHours);

        // Builds the JWT instance.
        JwtBuilder jwt = Jwts.builder()
                .subject(userId)
                .issuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName()));

        // Creates the user's role to be added to the JWT.
        Map<String, Role> roleClaim = createRoleClaim(role);

        // Adds the role to the JWT.
        for (Map.Entry<String, Role> claim : roleClaim.entrySet()) {
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

        return new HashMap<>(claimsJws.getPayload());
    }

    /**
     * Used to validate if the token is still valid, expired or malformed.
     *
     * @param token input JWT
     * @return a boolean value
     */
    public boolean isTokenValid(String token) {
        // Return false if the Token is blank or null.
        if (token == null)
            return false;

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

    /**
     * Create the role claim that is stored in the JWT for App security's policies.
     *
     * @return an object of type {@link Map}
     */
    private Map<String, Role> createRoleClaim(Role role) {
        Map<String, Role> claims = new HashMap<>();
        claims.put("role", role);
        return claims;
    }
}
