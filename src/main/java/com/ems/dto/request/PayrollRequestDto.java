package com.ems.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollRequestDto {

    @NotBlank(message = "username can not be blank")
    private String username;

    @NotBlank(message = "month should be in the MM YYYY format and not blank")
    private String month;

}
