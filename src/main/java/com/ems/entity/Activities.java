package com.ems.entity;


import com.ems.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Activities {

    private LocalDateTime activity;
    private ActivityType type;

}
