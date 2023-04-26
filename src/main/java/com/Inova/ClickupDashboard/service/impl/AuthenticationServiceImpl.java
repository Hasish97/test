package com.Inova.ClickupDashboard.service.impl;

import com.Inova.ClickupDashboard.dtos.LoggedUserDto;
import com.Inova.ClickupDashboard.dtos.LoginResponseDto;
import com.Inova.ClickupDashboard.request.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl {
    @Autowired
    Gson gson;

    @Value("${include_teams}")
    private  String include_teams;

    @Value("${recaptchaV3}")
    private  String  recaptchaV3;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<LoginResponseDto> authentication(User user) throws Exception {

        List<LoginResponseDto> loginResponseDto=new ArrayList<>();
        LoginResponseDto loginResponseDto1=new LoginResponseDto();

        if(!authenticateNonClickup(user).isEmpty()){
            loginResponseDto=authenticateNonClickup(user);
        }else {
        var httpClient = HttpClient.newBuilder().build();

        HashMap<String, String> params = new HashMap<>();
        params.put("include_teams", include_teams);
        params.put("recaptchaV3", recaptchaV3);

        var query = params.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        var host = "https://api.clickup.com";
        var pathname = "/v1/login";
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + pathname + '?' + query))
                .header("Authorization", getBasicAuthenticationHeader(user.getUsername(), user.getPassword()))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject= new JSONObject(response.body());

        if(jsonObject.length()==2){
            if(jsonObject.get("err")!=null){
                throw new AccessDeniedException((String) jsonObject.get("err"));
            }}
        else {
            loginResponseDto1 = gson.fromJson(response.body(), LoginResponseDto.class);
            loginResponseDto1.setLogin_status("success");
            loginResponseDto.add(loginResponseDto1);
        }
       }
        return loginResponseDto;
    }

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public List<LoginResponseDto> authenticateNonClickup(User user) throws Exception{
        List<LoginResponseDto> result = new ArrayList<>();
        Map<String, Object> params1 = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                          \n");
        SQL.append("FROM                                                              \n");
        SQL.append("    non_clickup_member                                            \n");
        SQL.append("WHERE                                                             \n");
        params1.put("user_name",user.getUsername());
        SQL.append("user_name =:user_name                                             \n");
        SQL.append("AND                                                               \n");
        params1.put("password",user.getPassword());
        SQL.append("password =:password                                               \n");

        return (List<LoginResponseDto>) namedParameterJdbcTemplate.query(SQL.toString(), params1, new ResultSetExtractor<List<LoginResponseDto>>() {

            @Nullable
            @Override
            public List<LoginResponseDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    LoginResponseDto loginResponseDto = new LoginResponseDto();
                    LoggedUserDto loggedUserDto=new LoggedUserDto();
                    loggedUserDto.setId(rs.getInt("id"));
                    loggedUserDto.setUsername(rs.getString("user_name"));
                    loggedUserDto.setEmail(rs.getString("name"));
                    loggedUserDto.setInitials(rs.getString("initials"));
                    loginResponseDto.setUser(loggedUserDto);
                    loginResponseDto.setLogin_status("success");
                    result.add(loginResponseDto);
                }
                return result;
            }
        });
    }
}
