package com.ems.controller;

import com.ems.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performance")
public class PerformanceController {


    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/all")   // Access: ADMIN
    public ResponseEntity<?> getAllPerformances() {
        return new ResponseEntity<>(performanceService.getAllPerformances(), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")   // Access: ALL USERS
    public ResponseEntity<?> getPerformanceByUsername(@PathVariable String username) {
        return ResponseEntity.ok(performanceService.getPerformanceByUsername(username));
    }
}
