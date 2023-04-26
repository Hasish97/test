package com.Inova.ClickupDashboard.service;

import com.Inova.ClickupDashboard.controllers.App_constants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.file.AccessDeniedException;


public class UserAuthentication {

    public static String getBearerTokenHeader(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
    public static String AuthenticateUser() throws Exception {
        String jwtToken = null;
        if(getBearerTokenHeader()==null||!getBearerTokenHeader().startsWith("Bearer")){
            throw new AccessDeniedException(App_constants.AuthorizationError.USER_UNAUTHORIZED.getDescription());
        }
        jwtToken = getBearerTokenHeader().substring(7);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity entity = new HttpEntity(headers);

        String result = restTemplate.postForObject("https://api.clickup.com/core/v1/devKey", entity, String.class);
        return result;
    }
}
