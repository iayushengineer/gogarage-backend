package com.app.utils;

import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

	private final SecretKey secretKey = Keys.hmacShaKeyFor("suyog@123suyog@123suyog@123suyog@123".getBytes());
	private final long JWT_EXPIRATION = 86400000; // 24 hours

	public String generateToken(Map<String, String> map) {

		String email = map.get("email");
		String password = map.get("password");
		String role = map.get("role");

		return Jwts.builder()
				.claim("email", email)
				.claim("password", password)
				.claim("role", role)
				.issuer("Suyog")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}

	public String getEmailFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.get("email", String.class);
	}

	public String getPasswordFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.get("password", String.class);
	}

	public String getRoleFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.get("role", String.class);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		try {
			String email = getEmailFromToken(token);
			return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
		} catch (Exception e) {
			return false;
		}
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getAllClaimsFromToken(token).getExpiration();
		return expiration.before(new Date());
	}
}
