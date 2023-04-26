package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoggedUserDto implements Serializable {
    private Integer id;
    private String username;
    private String initials;
    private String email;
}
