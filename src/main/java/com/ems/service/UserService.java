package com.ems.service;

import com.ems.dto.request.UserRequestDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.entity.User;
import com.ems.enums.Role;
import com.ems.mapper.ModelMapper;
import com.ems.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Transactional
    public UserResponseDto createUser(UserRequestDto user){

        boolean exists = userRepo.existsByEmail(user.getEmail());
        if(exists){
            throw new RuntimeException("User with this email already exists");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String username = generateEmployeeId(user.getRole());
        String password =String.valueOf((int) (Math.random() * 100000));
        log.info(password);
        String encodedPassword = passwordEncoder.encode(password);

        User newUser = ModelMapper.toUser(user);
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);

        emailService.sendEmail(user.getEmail(), "Welcome to EMS",
                "Your account has been created successfully. \nUsername: " + username + "\nPassword: " + password);
        User saved = userRepo.save(newUser);
        return ModelMapper.toUserResponseDto(saved);
    }



    private String generateEmployeeId(Role role) {
        long count = userRepo.countUserByRole(role) + 1;
        String username = "";
        if(role.equals(Role.ADMIN)){
            username = String.format("ADM%02d", count);
        }
        else{
            username = String.format("EMP%04d", count);
        }
         // EMP001, EMP002...
        return username;
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> res = userRepo.findAll();
        ArrayList<UserResponseDto> users = new ArrayList<>();
        for (User user : res) {
            users.add(ModelMapper.toUserResponseDto(user));
        }
        return users;
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        return ModelMapper.toUserResponseDto(user);
    }

    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
        User existingUser = userRepo.findByUsername(username);
        if(existingUser == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        existingUser.setName(userRequestDto.getName());
        existingUser.setPhone(userRequestDto.getPhone());
        existingUser.setEmail(userRequestDto.getEmail());
        existingUser.setAddress(userRequestDto.getAddress());
        existingUser.setDepartment(userRequestDto.getDepartment());

        User saved = userRepo.save(existingUser);
        return ModelMapper.toUserResponseDto(saved);
    }

    public void deleteUser(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        userRepo.delete(user);
    }

    public UserResponseDto updateByColumn(String username, String columnName, String value) {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        switch (columnName.toLowerCase()) {
            case "name":
                user.setName(value);
                break;
            case "phone":
                user.setPhone(value);
                break;
            case "email":
                user.setEmail(value);
                break;
            case "address":
                user.setAddress(value);
                break;
            case "role":
                user.setRole(Role.valueOf(value.toUpperCase()));
                break;
            case "department":
                user.setDepartment(value);
                break;
            default:
                throw new RuntimeException("Invalid column name: " + columnName);
        }

        User saved = userRepo.save(user);
        return ModelMapper.toUserResponseDto(saved);
    }
}
