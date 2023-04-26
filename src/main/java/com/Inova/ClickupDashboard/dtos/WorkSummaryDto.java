package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;


@Data
public class WorkSummaryDto implements Serializable {
    private Integer id;
    private String time_mseconds;
    private String time_hours;
    private String tracked_date;
    private String tracked_day;
    private String working_status;
}
