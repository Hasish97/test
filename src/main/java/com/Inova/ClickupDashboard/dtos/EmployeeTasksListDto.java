package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeTasksListDto {
    private String task_id;
    private String task_name;
    private String status;
    private String spent_time_mseconds;
    private String spent_time_hours;
    private List<WorkSummaryDto> tracked_times;
}
