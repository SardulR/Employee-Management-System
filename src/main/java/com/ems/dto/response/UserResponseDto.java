package com.ems.dto.response;

import com.ems.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String username;
    private String address;
    private String department;
    private Role role;
    private String hire_date;
    private float salary;
}
