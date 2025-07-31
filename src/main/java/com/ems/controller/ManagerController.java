package com.ems.controller;

import com.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    // This controller is for manager-specific operations
    @Autowired
    private UserService userService;


}
