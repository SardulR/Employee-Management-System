package com.ems.dto.request;

import com.ems.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequestDto {

    @NotBlank(message = "taskName cannot be blank")
    private String taskName;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotNull(message = "priority cannot be blank")
    private TaskPriority priority;
}
