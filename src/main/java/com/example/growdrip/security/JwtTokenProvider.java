package com.example.growdrip.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private Key secretKey;
    private final long validityInMs = 3600000; // 1 час
    //Инициализация секретного ключа после создания бина
    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor("G:Zw5[x4,nX/k~3%A9h@-]?T(=*j2N}q".getBytes());
    }

    //Генерация JWT токена, содержащего логин и зашифрованный пароль
    public String generateToken(String username, String hashedPassword) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject("user_auth") //Тема токена
                .claim("username", username)
                .claim("password", hashedPassword)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256) //Подпись токена
                .compact();
    }
    //Проверка токена на валидность
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Невалидный токен: {}", e.getMessage());
            return false;
        }
    }
    //Извлекаем username из токена
    public String getUsernameFromToken(String token) {
        return getClaims(token).get("username", String.class);
    }
    //Извлекаем хеш пароля из токена
    public String getPasswordFromToken(String token) {
        return getClaims(token).get("password", String.class);
    }
    //Метод для извлечения Claims из токена
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
