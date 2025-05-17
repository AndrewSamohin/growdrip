package com.example.growdrip.service.impl;

import com.example.growdrip.dto.UserDto;
import com.example.growdrip.entity.User;
import com.example.growdrip.mapper.UserMapper;
import com.example.growdrip.model.Role;
import com.example.growdrip.repository.UserRepository;
import com.example.growdrip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    //Регистрация нового пользователя
    @Override
    public UserDto registerUser(UserDto userDto) {
        //Проверка email на уникальность
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email уже используется");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Имя пользователя уже занято");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Set.of(Role.ROLE_ADMIN)); //По умолчанию роль админа у пользователя

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }
    //Проверяет, существует ли пользователь с данным email
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    //Проверяет, существует ли пользователь с данным username
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    //Находит пользователя по имени и возвращает его DTO
    @Override
    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}
