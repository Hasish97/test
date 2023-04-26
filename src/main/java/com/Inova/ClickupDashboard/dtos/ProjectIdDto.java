package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectIdDto implements Serializable {
    private String project_id;
    public ProjectIdDto(){};
    public ProjectIdDto(String project_id) {
        this.project_id = project_id;
    }
}
