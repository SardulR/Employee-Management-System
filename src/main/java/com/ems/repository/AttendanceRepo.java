package com.ems.repository;

import com.ems.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, String> {

    List<Attendance> findByUsername(String username);

    Attendance findByUsernameAndDate(String username, LocalDate date);

    @Query(value = "SELECT * FROM attendance WHERE EXTRACT(MONTH FROM date) = :month AND EXTRACT(YEAR FROM date) = :year", nativeQuery = true)
    List<Attendance> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

}
