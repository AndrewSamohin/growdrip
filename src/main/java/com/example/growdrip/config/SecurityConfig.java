package com.example.growdrip.config;

import com.example.growdrip.security.CustomUserDetailsService;
import com.example.growdrip.security.JwtAuthenticationFilter;
import com.example.growdrip.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers( //Разрешаем доступ к реализации
                                "/",
                                "/index.html",
                                "/auth.html",
                                "/css/**",
                                "/js/**",
                                "/images/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll() //Все HTTP OPTIONS-запросы разрешаем
                        .anyRequest().authenticated()                // Остальные требуют авторизации
                )
                //не создаём HTTP-сессии на сервере
                .sessionManagement(
                        sess -> sess
                                .sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(
                                tokenProvider,
                                userDetailsService),
                        UsernamePasswordAuthenticationFilter.class); // Регистрируем JWT фильтр

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5500")); // или "*"
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
