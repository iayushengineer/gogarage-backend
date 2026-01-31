package com.app.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setup() {
        jwtTokenUtil = new JwtTokenUtil();
    }

    @Test
    public void testGenerateToken() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("password", "password");
        claims.put("role", "CUSTOMER");

        // When
        String token = jwtTokenUtil.generateToken(claims);

        // Then
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.split("\\.")).hasSize(3); // JWT has 3 parts separated by dots
    }

    @Test
    public void testGetEmailFromToken() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("password", "password");
        claims.put("role", "CUSTOMER");
        String token = jwtTokenUtil.generateToken(claims);

        // When
        String email = jwtTokenUtil.getEmailFromToken(token);

        // Then
        assertThat(email).isEqualTo("test@example.com");
    }

    @Test
    public void testGetPasswordFromToken() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("password", "password123");
        claims.put("role", "CUSTOMER");
        String token = jwtTokenUtil.generateToken(claims);

        // When
        String password = jwtTokenUtil.getPasswordFromToken(token);

        // Then
        assertThat(password).isEqualTo("password123");
    }

    @Test
    public void testGetRoleFromToken() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("password", "password");
        claims.put("role", "ADMIN");
        String token = jwtTokenUtil.generateToken(claims);

        // When
        String role = jwtTokenUtil.getRoleFromToken(token);

        // Then
        assertThat(role).isEqualTo("ADMIN");
    }

    @Test
    public void testValidateTokenWithValidToken() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("password", "password");
        claims.put("role", "CUSTOMER");
        String token = jwtTokenUtil.generateToken(claims);

        UserDetails userDetails = User.builder()
                .username("test@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // When
        Boolean isValid = jwtTokenUtil.validateToken(token, userDetails);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    public void testValidateTokenWithInvalidUser() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("password", "password");
        claims.put("role", "CUSTOMER");
        String token = jwtTokenUtil.generateToken(claims);

        UserDetails userDetails = User.builder()
                .username("different@example.com") // Different email
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // When
        Boolean isValid = jwtTokenUtil.validateToken(token, userDetails);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void testValidateTokenWithMalformedToken() {
        // Given
        String malformedToken = "invalid.token.here";
        UserDetails userDetails = User.builder()
                .username("test@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // When
        Boolean isValid = jwtTokenUtil.validateToken(malformedToken, userDetails);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void testTokenContainsAllClaims() {
        // Given
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "admin@carservice.com");
        claims.put("password", "admin123");
        claims.put("role", "ADMIN");

        // When
        String token = jwtTokenUtil.generateToken(claims);

        // Then
        assertThat(jwtTokenUtil.getEmailFromToken(token)).isEqualTo("admin@carservice.com");
        assertThat(jwtTokenUtil.getPasswordFromToken(token)).isEqualTo("admin123");
        assertThat(jwtTokenUtil.getRoleFromToken(token)).isEqualTo("ADMIN");
    }
}