package com.ems.controller;

import com.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private UserService userService;

    // This controller is for employee-specific operations
//    @PutMapping("/update-user/{username}")
//    public ResponseEntity<?> updateUser(@PathVariable String username, @Valid @RequestBody UserDto userDto) {
//        User updatedUser = userService.updateUser(username, userDto);
//        return ResponseEntity.ok(updatedUser);
//    }

}
