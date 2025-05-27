package com.example.growdrip.controller;

import com.example.growdrip.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenInfoController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public ResponseEntity<?> getTokenInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7); // убираем "Bearer "

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }

        String username = jwtTokenProvider.getUsernameFromToken(token);
        String password = jwtTokenProvider.getPasswordFromToken(token);

        return ResponseEntity.ok(Map.of(
                "username", username,
                "password", password
        ));
    }
}
