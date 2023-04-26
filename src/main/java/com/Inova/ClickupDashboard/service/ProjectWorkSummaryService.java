package com.Inova.ClickupDashboard.service;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.exception.impl.AppsException;

import java.util.List;

public interface ProjectWorkSummaryService {
    List<ProjectWorkSummaryDto> getAllProjectWorkSummary(String project_id) throws AppsException;

    List<ProjectWorkSummaryDto> getAllProjectWorkSummaryWithinDates(String project_id, RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws AppsException;

    List<ProjectAssignersDetailsDto> getProjectAssignersDetails(RequestDateRangeDto requestDateRangeDto) throws AppsException;

    List<ProjectStatusSummaryCountDto> getProjectSummaryCount() throws AppsException;
}
