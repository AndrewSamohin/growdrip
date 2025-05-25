package com.example.growdrip.controller;

import com.example.growdrip.dto.JwtResponse;
import com.example.growdrip.dto.LoginRequest;
import com.example.growdrip.dto.UserDto;
import com.example.growdrip.service.AuthService;
import com.example.growdrip.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Data
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    //Эдпоинт для регистрации
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registered = userService.registerUser(userDto);
        return ResponseEntity.ok(registered);
    }

    //Эдпоинт для входа
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.authenticate(request);
        return ResponseEntity.ok(jwtResponse);
    }

}
