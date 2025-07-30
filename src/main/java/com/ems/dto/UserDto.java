package com.ems.dto;

import com.ems.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "Name Should not be Blank")
    private String name;

    @NotBlank(message = "Phone Should not be Blank")
    @Size(max = 10, message = "Phone Number Should be 10 Digit")
    private String phone;

    @NotBlank(message = "Email Should not be Blank")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Address Should not be Blank")
    private String address;

    @NotBlank(message = "Department Should not be Blank")
    private String department;

    @NotNull(message = "Name Should not be belongs to (Manager/Employee)")
    private Role role;


}
