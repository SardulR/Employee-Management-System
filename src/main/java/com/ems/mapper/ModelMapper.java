package com.ems.mapper;

import com.ems.dto.UserDto;
import com.ems.entity.User;

public class ModelMapper {

    public static User toUser(UserDto userDto){
        User newUser = new User().builder()
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .department(userDto.getDepartment())
                .role(userDto.getRole())
                .build();

        return newUser;
    }
}
