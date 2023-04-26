package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeWorkDaysCountDto implements Serializable {
    private Integer imp_id;
    private String imp_name;
    private String hours_count;
}
