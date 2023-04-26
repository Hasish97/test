package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestDateRangeDto implements Serializable {
    private String start_date;
    private String end_date;
}
