package com.ems.controller;

import com.ems.dto.request.UserRequestDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.entity.User;
import com.ems.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE NEW USER
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        UserResponseDto user = userService.createUser(userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // GET ALL USER
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //GET USER BY USERNAME
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    // UPDATE USER DETAILS
    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(username, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    //    CHANGE ROLE
    @PutMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestParam String username, @RequestParam String role) {
        // Logic to change the role of the user
        UserResponseDto user = userService.updateByColumn(username, "role", role);
        return ResponseEntity.ok("Role changed successfully");
    }

    // DELETE USER
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        // Logic to delete the user
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }
}
