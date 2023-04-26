package com.Inova.ClickupDashboard.controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
public class Task_controller {

    @GetMapping("/task")
    public Json getTask() throws IOException, InterruptedException, JSONException {

        var httpClient = HttpClient.newBuilder().build();

        HashMap<String, String> params = new HashMap<>();
        params.put("custom_task_ids", "true");
        params.put("team_id", "123");
        params.put("include_subtasks", "true");

        var query = params.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        var host = "https://api.clickup.com";
        var taskId = "864dr3k6e";
        var pathname = "/api/v2/task/" + taskId;
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + pathname + '?' + query))
                .header("Content-Type", "string")
                .header("Authorization", "pk_54940048_T5LJ3Q9YE8VV6USCXB8HCXQT4VBF9EBR")
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

       // System.out.println(response.body());
       // JSONObject jsonObject= new JSONObject(response.body());
      //  String assignee = (String) jsonObject.get("assignees");
       // String dueDate = (String) jsonObject.get("due_date");

    //    String priority = (String) jsonObject.get("priority");
     //   String status = (String) jsonObject.get("status");


        // System.out.println(response.body());
        return  new Json(response.body());
    }
}
