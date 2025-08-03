package com.ems.dto.request;

import com.ems.enums.PayRollStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PayrollUpdateRequest {
    @NotNull(message = "id should not be blank")
    private Long id;
    @NotBlank(message = "status should not be blank")
    private PayRollStatus status;
}
