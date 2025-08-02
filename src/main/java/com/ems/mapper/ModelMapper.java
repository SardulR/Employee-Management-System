package com.ems.mapper;

import com.ems.dto.request.LeaveRequestDto;
import com.ems.dto.request.UserRequestDto;
import com.ems.dto.response.LeaveResponseDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.entity.Leaves;
import com.ems.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {

    public static User toUser(UserRequestDto userRequestDto){
        User newUser = new User().builder()
                .name(userRequestDto.getName())
                .phone(userRequestDto.getPhone())
                .email(userRequestDto.getEmail())
                .address(userRequestDto.getAddress())
                .department(userRequestDto.getDepartment())
                .role(userRequestDto.getRole())
                .hire_date(userRequestDto.getHire_date())
                .salary(userRequestDto.getSalary())
                .build();

        return newUser;
    }

    public static UserResponseDto toUserResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getUserId());
        userResponseDto.setName(user.getName());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setAddress(user.getAddress());
        userResponseDto.setDepartment(user.getDepartment());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setHire_date(user.getHire_date());
        userResponseDto.setSalary(user.getSalary());
        return userResponseDto;
    }

    public static Leaves toLeave(LeaveRequestDto requestDto) {
        Leaves newLeave = new Leaves();
        newLeave.setStartDate(requestDto.getStartDate());
        newLeave.setEndDate(requestDto.getEndDate());
        newLeave.setReason(requestDto.getReason());
        return newLeave;
    }

    public static List<LeaveResponseDto> toLeaveResponseDto(List<Leaves> leaves) {
        List<LeaveResponseDto> response = new ArrayList<>();
        leaves.forEach( leaves1 -> {
            LeaveResponseDto dto = new LeaveResponseDto();
            dto.setId(leaves1.getId());
            dto.setUser(toUserResponseDto(leaves1.getUser()));
            dto.setStartDate(leaves1.getStartDate());
            dto.setEndDate(leaves1.getEndDate());
            dto.setReason(leaves1.getReason());
            dto.setStatus(leaves1.getStatus());
            dto.setCreatedAt(leaves1.getCreatedAt());

            response.add(dto);
        } );


        return response;
    }

}
