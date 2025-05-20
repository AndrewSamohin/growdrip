package com.example.growdrip.service;

import com.example.growdrip.dto.LoginRequest;
import com.example.growdrip.dto.UserDto;

public interface UserService {

    //Регистрация нового пользователя
    UserDto registerUser(UserDto userDto);

    //Проверяет есть ли пользоваетль с такой почтой
    boolean existsByEmail(String email);

    //Проверяет есть ли пользователь с таким логином
    boolean existsByUsername(String username);

    //Загружает пользователя по username
    UserDto findByUsername(String username);

    //Для входа в аккаунт
    boolean authenticateUser(LoginRequest loginRequest);

}
