package com.ems.mapper;

import com.ems.dto.request.UserRequestDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.entity.User;

public class ModelMapper {

    public static User toUser(UserRequestDto userRequestDto){
        User newUser = new User().builder()
                .name(userRequestDto.getName())
                .phone(userRequestDto.getPhone())
                .email(userRequestDto.getEmail())
                .address(userRequestDto.getAddress())
                .department(userRequestDto.getDepartment())
                .role(userRequestDto.getRole())
                .build();

        return newUser;
    }

    public static UserResponseDto toUserResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setAddress(user.getAddress());
        userResponseDto.setDepartment(user.getDepartment());
        userResponseDto.setRole(user.getRole());
        return userResponseDto;
    }
}
