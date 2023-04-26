package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectStatusSummaryDto implements Serializable {
    private String project_id;
    private String status;
}
