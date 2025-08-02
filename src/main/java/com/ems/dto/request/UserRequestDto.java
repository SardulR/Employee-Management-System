package com.ems.dto.request;

import com.ems.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "name Should not be Blank")
    private String name;

    @NotBlank(message = "phone Should not be Blank")
    @Size(max = 10, message = "Phone Number Should be 10 Digit")
    private String phone;

    @NotBlank(message = "email Should not be Blank")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "address Should not be Blank")
    private String address;

    @NotBlank(message = "department Should not be Blank")
    private String department;

    @NotNull(message = "role Should not be belongs to (Manager/Employee)")
    private Role role;

    @NotBlank(message = "hire_date Should not be Blank")
    private String hire_date;

    @NotNull(message = "salary Should not be Blank")
    private float salary;

}
