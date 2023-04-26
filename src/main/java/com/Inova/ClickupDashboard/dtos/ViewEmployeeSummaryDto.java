package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ViewEmployeeSummaryDto implements Serializable {
    private int emp_id;
    private String name;
    private String position="Employee";
    List<TotalWork> totalWorks;
}
