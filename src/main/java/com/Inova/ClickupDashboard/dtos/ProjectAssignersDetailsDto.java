package com.Inova.ClickupDashboard.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectAssignersDetailsDto implements Serializable {
    private String project_id;
    private String project_name;
    private String status;
    private List<TotalWork> totalWorks;
    private List<Assignees> assignees;
    private List<ProjectMasterDataDto> checkPointsMstData;
}

