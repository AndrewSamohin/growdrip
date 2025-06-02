package com.example.growdrip.service;

import com.example.growdrip.dto.JwtResponse;
import com.example.growdrip.dto.LoginRequest;
import com.example.growdrip.entity.User;
import com.example.growdrip.repository.UserRepository;
import com.example.growdrip.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//Обрабатывает логику авторизации
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    //Аутентификация и генерация токена
    public JwtResponse authenticate(LoginRequest request) {
        //Проводим аутентификацию
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                        request.getPassword())
        );

        //Получим пользователя из БД
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        //Генерация access токена с логином и паролем
        String accessToken = jwtTokenProvider.generateToken(user.getUsername(), user.getPassword());

        return new JwtResponse(accessToken);
    }

}
