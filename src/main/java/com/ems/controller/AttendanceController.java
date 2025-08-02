package com.ems.controller;

import com.ems.entity.Attendance;
import com.ems.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // CHECK IN FOR ATTENDANCE  ACCESS: ALL USERS, AUTHENTICATED

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        String res = attendanceService.checkIn(username);
        return new  ResponseEntity<>(Map.of("message", res), HttpStatus.OK);
    }

    // CHECK OUT FOR ATTENDANCE  ACCESS: ALL USERS, AUTHENTICATED
    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        String res = attendanceService.checkOut(username);
        return new  ResponseEntity<>(Map.of("message", res), HttpStatus.OK);
    }

    // GET ALL ATTENDANCE ACTIVITIES   ACCESS : ADMIN, AUTHENTICATED
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<Attendance> allAttendance = attendanceService.getAllAttendance();
        return new  ResponseEntity<>(allAttendance, HttpStatus.OK);
    }

    // GET ALL ATTENDANCE OF A USER    ACCESS : ALL USER, AUTHENTICATED
    @GetMapping("/user")
    public ResponseEntity<?> getAttendance(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        List<Attendance> attendance = attendanceService.findAllAttendanceOfUser(auth.getName());
        return new  ResponseEntity<>(attendance, HttpStatus.OK);
    }

    // GET ATTENDANCE OF TODAY ONLY   ACCESS : ALL USER, AUTHENTICATED
    @GetMapping("/user/today")
    public ResponseEntity<?> getTodayAttendance(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        Attendance attendance = attendanceService.checkAttendance(auth.getName());
        return new  ResponseEntity<>(attendance, HttpStatus.OK);
    }
}
