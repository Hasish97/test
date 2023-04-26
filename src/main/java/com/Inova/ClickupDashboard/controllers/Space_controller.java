package com.Inova.ClickupDashboard.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api")
public class Space_controller {

    @GetMapping("/space")
    public Json getSpace() throws IOException, InterruptedException {

        var httpClient = HttpClient.newBuilder().build();

        var host = "https://api.clickup.com";
        var spaceId = "90070028879";
        var pathname = "/api/v2/space/" + spaceId;
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + pathname ))
                .header("Authorization", "pk_55032694_4M6DND7JJX7P3BDNOSTKOFXD4ATQ3C74")
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      //  System.out.println(response.body());

        return  new Json(response.body());

    }
}
