package com.example.growdrip.config;

import com.example.growdrip.security.CustomUserDetailsService;
import com.example.growdrip.security.JwtAuthenticationFilter;
import com.example.growdrip.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF, так как используем JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  //Разрешаем доступ к реализации
                        .anyRequest().authenticated()                // Остальные требуют авторизации
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class); // Регистрируем JWT фильтр

        return http.build();
    }

    @Bean
    // Заменю позже, пока так. Возможно на BCrypt для шифрования паролей
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //Менеджер аутентификации (нужен для логина)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
