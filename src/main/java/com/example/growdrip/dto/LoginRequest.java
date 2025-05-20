package com.example.growdrip.dto;

import lombok.Data;
//Получение данных от пользователя
@Data
public class LoginRequest {

    private String username;
    private String password;

}
