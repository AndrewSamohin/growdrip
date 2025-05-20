package com.example.growdrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    //Web общается с Back хы)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")  // применимо ко всем эндпоинтам
                        .allowedOrigins("http://localhost:5500")  // разрешённый origin
                        .allowedMethods("*") // разрешаем любые HTTP-методы (GET, POST и т.д.)
                        .allowedHeaders("*"); // разрешаем любые заголовки
            }
        };
    }
}
