package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeDaysCountListDto implements Serializable {
    private Integer year;
    private Integer month;
    private String month_name;
    private List<EmployeeWorkDaysCountDto> employeeWorkHoursCount;
}
