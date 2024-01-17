package com.theeagleeyeproject.eaglewings.utility;

import com.theeagleeyeproject.eaglewings.security.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JwtUtilTest {

    private final String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZGY0NDY2Mi00ZTk2LTQ2MjEtOTQyYy05ZDM5OTUyZWVhZjAiLCJpYXQiOjE3MDE5MDYzMTMsImV4cCI6MTcwMTk5MjcxMywiY2xhaW1fa2V5MSI6ImNsYWltX3ZhbHVlMSJ9.dCObXlU_MEO7QZYHtTeO7MBy74koWk1DEuJaXtdRk-A";

    private final String secret = "someKeyVal;ljpi9uj09j-okue1-20894-08";

    private String newJwt;

    @BeforeEach
    void init() {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setSecret(secret);
        jwtUtil.setExpirationHours(24L);
        newJwt = jwtUtil.generateToken("164031b4-da97-4da9-aa10-683d01bb97e1", Role.USER);
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
        Assertions.assertEquals(Role.USER.toString(), claims.get("role"));
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