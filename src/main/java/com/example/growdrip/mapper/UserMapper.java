package com.example.growdrip.mapper;

import com.example.growdrip.dto.UserDto;
import com.example.growdrip.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
