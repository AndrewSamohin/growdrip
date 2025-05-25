package com.example.growdrip.dto;

import com.example.growdrip.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    //Данные, которые мы будет передавать клиенту
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;

}
