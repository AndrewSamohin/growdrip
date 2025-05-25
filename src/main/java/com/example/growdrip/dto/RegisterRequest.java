package com.example.growdrip.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    //Входные данные при регистрации (от клиента к серверу)
    private String username;
    private String email;
    private String password;

}
