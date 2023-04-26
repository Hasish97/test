package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeWorkingHoursAverageDto implements Serializable {
    private String month;
    private Integer employee_count;
    private String average_working_hours;
    private String average_working_milliseconds;
}
