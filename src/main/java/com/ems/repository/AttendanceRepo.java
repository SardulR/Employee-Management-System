package com.ems.repository;

import com.ems.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, String> {

    List<Attendance> findByUsername(String username);

    Attendance findByUsernameAndDate(String username, LocalDate date);

}
