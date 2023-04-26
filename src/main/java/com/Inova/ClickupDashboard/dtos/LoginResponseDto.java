package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResponseDto implements Serializable {
    private String login_status;
    private String token;
    private LoggedUserDto user;
}
