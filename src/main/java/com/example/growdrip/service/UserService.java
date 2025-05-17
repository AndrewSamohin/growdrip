package com.example.growdrip.service;

import com.example.growdrip.dto.RegisterRequest;
import com.example.growdrip.dto.UserDto;
import com.example.growdrip.entity.User;

import java.util.Optional;

public interface UserService {

    //Регистрация нового пользователя
    UserDto registerUser(UserDto userDto);

    //Проверяет есть ли пользоваетль с такой почтой
    boolean existsByEmail(String email);

    //Проверяет есть ли пользователь с таким логином
    boolean existsByUsername(String username);

    //Загружает пользователя по username
    UserDto findByUsername(String username);

}
