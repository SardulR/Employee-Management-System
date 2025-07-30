package com.ems.service;

import com.ems.dto.UserDto;
import com.ems.entity.User;
import com.ems.enums.Role;
import com.ems.mapper.ModelMapper;
import com.ems.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public User createUser(UserDto user){

        boolean exists = userRepo.existsByEmail(user.getEmail());
        if(exists){
            throw new RuntimeException("User with this email already exists");
        }

        User newUser = ModelMapper.toUser(user);
        newUser.setUsername(generateEmployeeId(user.getRole()));
        newUser.setPassword(String.valueOf((int)(Math.random()*100000)));

        return userRepo.save(newUser);
    }

    private String generateEmployeeId(Role role) {
        long count = userRepo.count() + 1;
        String username = "";
        if(role.equals(Role.MANAGER)){
            return String.format("MGR%03d", count);
        }
        return String.format("EMP%04d", count);
         // EMP001, EMP002...
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        return user;
    }

    public User updateUser(String username, UserDto userDto) {
        User existingUser = userRepo.findByUsername(username);
        if(existingUser == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        existingUser.setName(userDto.getName());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setDepartment(userDto.getDepartment());

        return userRepo.save(existingUser);
    }

    public void deleteUser(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        userRepo.delete(user);
    }

    public User updateByColumn(String username, String columnName, String value) {
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

        return userRepo.save(user);
    }
}
