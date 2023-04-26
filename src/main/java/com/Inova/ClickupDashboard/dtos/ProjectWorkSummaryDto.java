package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectWorkSummaryDto implements Serializable {
    private String project_Id;
    private String status;
    private String task_name;
    private String task_Id;
    private String working_hours;
    private String working_mseconds;
}
