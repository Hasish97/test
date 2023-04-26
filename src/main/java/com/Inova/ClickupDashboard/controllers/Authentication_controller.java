package com.Inova.ClickupDashboard.controllers;


import com.Inova.ClickupDashboard.dtos.LoginResponseDto;
import com.Inova.ClickupDashboard.exception.aop.ResponseExceptionHandler;
import com.Inova.ClickupDashboard.request.User;
import com.Inova.ClickupDashboard.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
public class Authentication_controller {
    @Autowired
    AuthenticationServiceImpl authenticationService;

    @ResponseExceptionHandler
    @PostMapping("/auth_user")
    public ResponseEntity<List<LoginResponseDto>> getAuthenticate(  @RequestBody User user) throws Exception {
        List<LoginResponseDto> loginResponseDto=this.authenticationService.authentication(user);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

}
