package com.Inova.ClickupDashboard.service;

import com.Inova.ClickupDashboard.dtos.ProjectCheckPointsDto;
import com.Inova.ClickupDashboard.dtos.ProjectDto;
import com.Inova.ClickupDashboard.dtos.ProjectMasterDataDto;
import com.Inova.ClickupDashboard.exception.impl.AppsException;

import java.util.List;

public interface ProjectService {
    List<ProjectCheckPointsDto> getProjectCheckpointsPercentages(String project_id);
    List<ProjectMasterDataDto> viewProjectMasterData(String projectId) throws AppsException;
    ProjectMasterDataDto updateProjectMasterData(ProjectMasterDataDto project);
    ProjectMasterDataDto saveProject(ProjectMasterDataDto project) throws AppsException;
    ProjectDto viewProject(String project_id);
    List<ProjectDto> viewAllProjects();
}
