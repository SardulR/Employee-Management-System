package com.ems.dto.response;

import com.ems.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveResponseDto {

    private String id;

    private UserResponseDto user;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private Status status;

    private LocalDateTime createdAt;
}
