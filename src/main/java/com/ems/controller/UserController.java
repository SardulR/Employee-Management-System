package com.ems.controller;

import com.ems.dto.request.UserRequestDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE NEW USER  ACCESS: ADMIN
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        UserResponseDto user = userService.createUser(userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // GET ALL USER  ACCESS: ADMIN
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //GET USER BY USERNAME /// ACCESSIBLE FOR ALL USERS
    @GetMapping("/me")
    public ResponseEntity<?> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getUserByUsername(authentication.getName()));
    }

    // UPDATE USER DETAILS ACCESS: ALL
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponseDto updatedUser = userService.updateUser(authentication.getName(), userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    //    CHANGE ROLE ACCESS: ADMIN
    @PutMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestParam String username, @RequestParam String role) {
        // Logic to change the role of the user
        UserResponseDto user = userService.updateByColumn(username, "role", role);
        return ResponseEntity.ok("Role changed successfully");
    }

    // DELETE USER ACCESS: ADMIN
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        // Logic to delete the user
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

}
