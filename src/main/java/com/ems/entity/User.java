package com.ems.entity;


import com.ems.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    private String address;

    @NotNull
    private String department;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;


}
