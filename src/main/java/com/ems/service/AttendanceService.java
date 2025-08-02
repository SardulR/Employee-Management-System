package com.ems.service;

import com.ems.entity.Attendance;
import com.ems.enums.AttendanceStatus;
import com.ems.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {


    @Autowired
    private AttendanceRepo attendanceRepo;


    public String checkIn(String username){

        Attendance checkAlreadyExist = attendanceRepo.findByUsernameAndDate(username, LocalDate.now());
        if(checkAlreadyExist != null){

            throw new RuntimeException("Your Today Attendance already marked");
        }

        Attendance attendance = new Attendance().builder()
                .username(username)
                .date(LocalDate.now())
                .check_in(LocalDateTime.now())
                .created_at(LocalDateTime.now())
                .status(AttendanceStatus.CHECKEDIN)
                .build();

        attendanceRepo.save(attendance);
        return "success";

    }

    public String checkOut(String username){

        Attendance attendance = attendanceRepo.findByUsernameAndDate(username,LocalDate.now());

                if(attendance!=null){
                    //TOTAL HOUR CALCULATION
                    int check_out_hour = LocalDateTime.now().getHour();
                    int check_out_minute = LocalDateTime.now().getMinute();
                    int check_in_hour = attendance.getCheck_in().getHour();
                    int check_in_minute = attendance.getCheck_in().getMinute();
                    float total_hour = (float) ((check_out_hour * 60 + check_out_minute) - (check_in_hour * 60 + check_in_minute)) /60;


                    attendance.setCheck_out(LocalDateTime.now());
                    attendance.setTotal_hour(total_hour);
                    attendance.setStatus(AttendanceStatus.CHECKEDOUT);
                    attendanceRepo.save(attendance);
                    return "success";
                }else{
                    throw new RuntimeException("Attendance not found");
                }

    }


    public List<Attendance> getAllAttendance(){
        return attendanceRepo.findAll();
    }


    public Attendance checkAttendance(String username){
        return attendanceRepo.findByUsernameAndDate(username,LocalDate.now());

    }

    public List<Attendance> findAllAttendanceOfUser(String username){
        return attendanceRepo.findByUsername(username);
    }


}
