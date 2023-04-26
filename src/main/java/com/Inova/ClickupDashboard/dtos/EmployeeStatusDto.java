package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeStatusDto implements Serializable {
    private int full_count;
    private int active_users;
    private int partially_active_users;
    private int inactive_users;
}
