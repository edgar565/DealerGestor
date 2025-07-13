package com.edgarsannic.dealergestor.jwt;

import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    // Inyectamos la clave secreta (Base64) desde application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Duración del token en milisegundos (por ejemplo, 1 día)
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Genera la clave de firma a partir del secreto Base64.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un JWT con subject = username y claim "role".
     */
    public String generateToken(CompanyUserEntity user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                // Header explícito (opcional)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Parsea y devuelve los claims del token.
     */
    private Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    /**
     * Extrae el username (subject) del token.
     */
    public String extractUsername(String token) {
        try {
            return parseToken(token).getBody().getSubject();
        } catch (JwtException ex) {
            log.error("Falló extractUsername: {}", ex.getMessage());
            return null;
        }
    }

    /**
     * Verifica si el token está expirado.
     */
    private boolean isTokenExpired(String token) {
        try {
            Date expiration = parseToken(token).getBody().getExpiration();
            return expiration.before(new Date());
        } catch (JwtException ex) {
            log.error("Falló isTokenExpired: {}", ex.getMessage());
            return true;
        }
    }

    /**
     * Valida que el token corresponda al usuario y no esté expirado.
     */
    public boolean validateToken(String token, CompanyUserEntity user) {
        try {
            String username = extractUsername(token);
            return username != null
                    && username.equals(user.getUsername())
                    && !isTokenExpired(token);
        } catch (JwtException ex) {
            log.error("Token inválido o expirado: {}", ex.getMessage());
            return false;
        }
    }
}
