package com.senac.barksotre.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final Key signingKey;
	private final long expirationMs;

	public JwtService(
			@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-ms}") long expirationMs) {
		if (secret.length() < 32) {
			throw new IllegalArgumentException("app.jwt.secret deve ter pelo menos 32 caracteres");
		}
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMs = expirationMs;
	}

	public String generateToken(UserDetails user) {
		Date now = new Date();
		return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(now)
				.expiration(new Date(now.getTime() + expirationMs))
				.signWith(signingKey)
				.compact();
	}

	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}

	public boolean isValid(String token, UserDetails user) {
		Claims claims = parseClaims(token);
		return claims.getSubject().equals(user.getUsername())
				&& claims.getExpiration().after(new Date());
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().verifyWith((javax.crypto.SecretKey) signingKey).build()
				.parseSignedClaims(token).getPayload();
	}

	public long getExpirationMs() {
		return expirationMs;
	}
}
