package com.ems.controller;

import com.ems.dto.request.LeaveRequestDto;
import com.ems.entity.Leaves;
import com.ems.service.LeavesService;
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

    // CREATE NEW LEAVE REQUEST
    @PostMapping
    public ResponseEntity<?> addLeave(@RequestBody LeaveRequestDto leavesRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Leaves leave = leavesService.createLeave(leavesRequest, authentication.getName());
        return ResponseEntity.ok(Map.of("message", "Leave has been added successfully", "leave", leave));
    }

    // GET ALL LEAVES
    @GetMapping("/all")
    public ResponseEntity<?> getAllLeaves() {
        return ResponseEntity.ok(leavesService.getAllLeaves());
    }

    // GET LEAVES BY USERNAME
    @GetMapping("/user")
    public ResponseEntity<?> getLeavesByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(leavesService.getLeavesByUsername(authentication.getName()));
    }

    // APPROVE LEAVE
    @PutMapping("/approve/{leaveId}")
    public ResponseEntity<?> approveLeave(@PathVariable String leaveId) {
        Leaves approvedLeave = leavesService.approveLeave(leaveId);
        return ResponseEntity.ok("Leave approved successfully: " + approvedLeave.getId());
    }
    // REJECT LEAVE
    @PutMapping("/reject/{leaveId}")
    public ResponseEntity<?> rejectLeave(@PathVariable String leaveId) {
        Leaves rejectedLeave = leavesService.rejectLeave(leaveId);
        return ResponseEntity.ok("Leave rejected successfully: " + rejectedLeave.getId());
    }
}
