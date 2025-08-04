package com.ems.controller;

import com.ems.dto.request.LeaveRequestDto;
import com.ems.entity.Leaves;
import com.ems.service.LeavesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/leaves")
public class LeavesController {


    @Autowired
    private LeavesService leavesService;

    // CREATE NEW LEAVE REQUEST  ACCESS: ALL USERS, AUTHENTICATED
    @PostMapping
    public ResponseEntity<?> addLeave(@Valid @RequestBody LeaveRequestDto leavesRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Leaves leave = leavesService.createLeave(leavesRequest, authentication.getName());
        return ResponseEntity.ok(Map.of("message", "Leave has been added successfully", "leave", leave));
    }

    // GET ALL LEAVES  ACCESS: ADMIN, AUTHENTICATED
    @GetMapping("/all")
    public ResponseEntity<?> getAllLeaves() {
        return ResponseEntity.ok(leavesService.getAllLeaves());
    }

    // GET LEAVES BY USERNAME ACCESS: ALL USERS, AUTHENTICATED
    @GetMapping("/user")
    public ResponseEntity<?> getLeavesByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(leavesService.getLeavesByUsername(authentication.getName()));
    }

    // APPROVE LEAVE  ACCESS: ADMIN, AUTHENTICATED
    @PutMapping("/approve/{leaveId}")
    public ResponseEntity<?> approveLeave(@PathVariable String leaveId) {
        Leaves approvedLeave = leavesService.approveLeave(leaveId);
        return ResponseEntity.ok("Leave approved successfully: " + approvedLeave.getId());
    }
    // REJECT LEAVE   ACCESS: ADMIN, AUTHENTICATED
    @PutMapping("/reject/{leaveId}")
    public ResponseEntity<?> rejectLeave(@PathVariable String leaveId) {
        Leaves rejectedLeave = leavesService.rejectLeave(leaveId);
        return ResponseEntity.ok("Leave rejected successfully: " + rejectedLeave.getId());
    }
}
