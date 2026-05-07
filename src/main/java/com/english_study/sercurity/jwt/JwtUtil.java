package com.english_study.sercurity.jwt;

import com.english_study.model.dto.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key (>= 32 ký tự cho HS256)
    private static final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    // 1 giờ
    private static final long EXPIRATION = 1000 * 60 * 60;

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * Tạo JWT token
     */
    public String generateToken(UserDTO user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("type", "access")// userId
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("role", user.getRoleId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Lấy toàn bộ claims từ token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Lấy userId từ token
     */
    public String extractUserID(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Lấy name
     */
    public String extractName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    /**
     * Lấy email
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    /**
     * Lấy phoneNumber
     */
    public Long extractPhoneNumber(String token) {
        return extractAllClaims(token).get("phoneNumber", Long.class);
    }

    /**
     * Lấy role
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Lấy expiration
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Kiểm tra token hết hạn chưa
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {

            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .claim("type", "refresh")
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);

            // check type
            String type = claims.get("type", String.class);
            if (!"refresh".equals(type)) {
                return false;
            }

            // check expire
            return !claims.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
           throw  new RuntimeException("Invalid refresh token");
        }
    }
}