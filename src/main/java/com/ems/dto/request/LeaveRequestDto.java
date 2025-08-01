package com.ems.dto.request;



import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;


@Data
public class LeaveRequestDto {

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Reason cannot be null")
    private String reason;
}
