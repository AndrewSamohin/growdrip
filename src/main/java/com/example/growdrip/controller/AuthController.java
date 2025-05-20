package com.example.growdrip.controller;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Data
public class AuthController {

    private final AuthenticationManager authenticationManager;


}
