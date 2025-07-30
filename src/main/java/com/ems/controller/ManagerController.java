package com.ems.controller;

import com.ems.dto.UserDto;
import com.ems.entity.User;
import com.ems.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    // This controller is for manager-specific operations
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
        if(!userDto.getRole().toString().equals("EMPLOYEE")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new HashMap<String, String>() {{
                        put("error", "Manager can only create employees");
                    }});

        }
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update-user/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(username, userDto);
        return ResponseEntity.ok(updatedUser);
    }
}
