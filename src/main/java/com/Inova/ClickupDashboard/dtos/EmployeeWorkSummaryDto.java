package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeWorkSummaryDto implements Serializable {
    private int emp_id;
    private String name;
    private String total_work_milisoconds;
    private String total_work_hours;
    private List<WorkSummaryDto> work_summary;
}
