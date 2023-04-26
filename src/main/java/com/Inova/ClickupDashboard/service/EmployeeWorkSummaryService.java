package com.Inova.ClickupDashboard.service;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.exception.impl.AppsException;

import java.text.ParseException;
import java.util.List;

public interface EmployeeWorkSummaryService {
    List<EmployeeWorkSummaryDto> getEmployeeWorkSummary(RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws AppsException;
    List<EmployeeTasksListDto> getEmployeeTaskSummary(Integer member_id, RequestDateRangeDto requestDateRangeDto) throws AppsException, ParseException;
    List<ViewEmployeeSummaryDto> getViewEmployeeSummary(RequestDateRangeDto requestDateRangeDto) throws AppsException;
    List<ViewEmployeeSummaryDto> getWorkForProject(String project_id, RequestDateRangeDto requestDateRangeDto) throws AppsException;
    List<EmployeeDaysCountListDto> employeeWorkDaysCount() throws Exception;
    List<EmployeeWorkingHoursAverageDto> employeeWorkDaysAverage() throws Exception;
}
