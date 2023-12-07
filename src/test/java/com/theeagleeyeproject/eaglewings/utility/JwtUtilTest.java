package com.theeagleeyeproject.eaglewings.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class JwtUtilTest {

    private final String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZGY0NDY2Mi00ZTk2LTQ2MjEtOTQyYy05ZDM5OTUyZWVhZjAiLCJpYXQiOjE3MDE5MDYzMTMsImV4cCI6MTcwMTk5MjcxMywiY2xhaW1fa2V5MSI6ImNsYWltX3ZhbHVlMSJ9.dCObXlU_MEO7QZYHtTeO7MBy74koWk1DEuJaXtdRk-A";

    private final String secret = "someKeyVal;ljpi9uj09j-okue1-20894-08";

    @Test
    void generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("claim_key1", "claim_value1");
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        jwtUtil.setExpirationHours(24L);
        String jwt = jwtUtil.generateToken(UUID.randomUUID().toString(), claims);

        Assertions.assertNotNull(jwt, "Jwt came back from the method NULL");
    }

    @Test
    void extractClaim() {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        Map<String, Object> claims = jwtUtil.extractClaims(jwt);
        Assertions.assertEquals("claim_value1", claims.get("claim_key1"));
    }


    @Test
    void isTokenValid() {
        String invalidJwt = "yJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZGY0NDY2Mi00ZTk2LTQ2MjEtOTQyYy05ZDM5OTUyZWVhZjAiLCJpYXQiOjE3MDE5MDYzMTMsImV4cCI6MTcwMTk5MjcxMywiY2xhaW1fa2V5MSI6ImNsYWltX3ZhbHVlMSJ9.dCObXlU_MEO7QZYHtTeO7MBy74koWk1DEuJaXtdRk-A";
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        boolean invalidJwtResult = jwtUtil.isTokenValid(invalidJwt);
        Assertions.assertFalse(invalidJwtResult);
    }
}