package com.ems.controller;

import com.ems.dto.UserDto;
import com.ems.entity.User;
import com.ems.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // YAHA PAR JO LIKHENGE VO ADMIN KE LIYE HOGA ADMIN KE ALAVA KOI BHI USER IS CONTROLLER KO ACCESS NAHI KAR SAKTA

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

//    CHANGE ROLE
    @PutMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestParam String username, @RequestParam String role) {
        // Logic to change the role of the user
        User user = userService.updateByColumn(username, "role", role);
        return ResponseEntity.ok("Role changed successfully");
    }

    // DELETE USER
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        // Logic to delete the user
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }
    //ALL USERS
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}



