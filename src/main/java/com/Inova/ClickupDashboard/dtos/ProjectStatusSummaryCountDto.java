package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectStatusSummaryCountDto implements Serializable {
    private Integer completed;
    private Integer pending;
}
