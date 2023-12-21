package com.theeagleeyeproject.eaglewings.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class JwtUtilTest {

    private final String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZGY0NDY2Mi00ZTk2LTQ2MjEtOTQyYy05ZDM5OTUyZWVhZjAiLCJpYXQiOjE3MDE5MDYzMTMsImV4cCI6MTcwMTk5MjcxMywiY2xhaW1fa2V5MSI6ImNsYWltX3ZhbHVlMSJ9.dCObXlU_MEO7QZYHtTeO7MBy74koWk1DEuJaXtdRk-A";

    private final String secret = "someKeyVal;ljpi9uj09j-okue1-20894-08";

    private String newJwt;

    @BeforeEach
    void init() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "user");
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        jwtUtil.setExpirationHours(24L);
        newJwt = jwtUtil.generateToken("8400bc7c-e6c8-42da-bd8e-34fbbe90c3d6", claims);
        System.out.println(newJwt);
    }

    @Test
    void generateToken() {
        Assertions.assertNotNull(newJwt, "Jwt came back from the method NULL");
    }

    @Test
    void extractClaim() {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        Map<String, Object> claims = jwtUtil.extractClaims(newJwt);
        Assertions.assertEquals("user", claims.get("role"));
    }


    @Test
    void isTokenValid() {
        String invalidJwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZGY0NDY2Mi00ZTk2LTQ2MjEtOTQyYy05ZDM5OTUyZWVhZjAiLCJpYXQiOjE3MDE5MDYzMTMsImV4cCI6MTcwMTk5MjcxMywiY2xhaW1fa2V5MSI6ImNsYWltX3ZhbHVlMSJ9.dCObXlU_MEO7QZYHtTeO7MBy74koWk1DEuJaXtdRk-A";
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        boolean invalidJwtResult = jwtUtil.isTokenValid(invalidJwt);
        Assertions.assertFalse(invalidJwtResult);
    }
}