package com.Inova.ClickupDashboard.controllers;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.exception.aop.ResponseExceptionHandler;
import com.Inova.ClickupDashboard.service.impl.EmployeeWorkSummaryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Inova.ClickupDashboard.service.UserAuthentication.AuthenticateUser;

@RestController
@RequestMapping("/api/employee")
public class Employee_summary_controller {
    @Autowired
    EmployeeWorkSummaryImpl employeeWorkSummary;

    @ResponseExceptionHandler
    @PostMapping("/get_employee_work_summary")
    public ResponseEntity<List<EmployeeWorkSummaryDto>> getEmployeeWorkSummaryWithinDates(@RequestBody RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws Exception{
        AuthenticateUser();
        List<EmployeeWorkSummaryDto> employeeWorkSummaryDto = this.employeeWorkSummary.getEmployeeWorkSummary(requestEmpWorkSummaryDto);
        return new ResponseEntity<>(employeeWorkSummaryDto, HttpStatus.OK);
    }
    @ResponseExceptionHandler
    @PostMapping("/get_employee_task_details/{member_id}")
    public ResponseEntity<List<EmployeeTasksListDto>> getEmployeeTaskSummary(@PathVariable Integer member_id, @RequestBody RequestDateRangeDto requestDateRangeDto) throws Exception{
        AuthenticateUser();
        List<EmployeeTasksListDto> employeeTasksListDto = this.employeeWorkSummary.getEmployeeTaskSummary(member_id,requestDateRangeDto);
        return new ResponseEntity<>(employeeTasksListDto, HttpStatus.OK);
    }
    @ResponseExceptionHandler
    @PostMapping("/get_work_for_project/{project_Id}")
    public ResponseEntity<List<ViewEmployeeSummaryDto>> getWorkForProject(@PathVariable String project_Id,@RequestBody RequestDateRangeDto requestDateRangeDto) throws Exception{
        AuthenticateUser();
        List<ViewEmployeeSummaryDto> viewEmployeeSummary = this.employeeWorkSummary.getWorkForProject(project_Id,requestDateRangeDto);
        return new ResponseEntity<>(viewEmployeeSummary, HttpStatus.OK);
    }
    @ResponseExceptionHandler
    @PostMapping("/view_employee_summary")
    public ResponseEntity<List<ViewEmployeeSummaryDto>> viewEmployeeSummary(@RequestBody RequestDateRangeDto requestDateRangeDto) throws Exception{
        List<ViewEmployeeSummaryDto> viewEmployeeSummary = this.employeeWorkSummary.getViewEmployeeSummary(requestDateRangeDto);
        return new ResponseEntity<>(viewEmployeeSummary, HttpStatus.OK);
    }
    @ResponseExceptionHandler
    @PostMapping("/view_employee_status")
    public ResponseEntity<List<EmployeeStatusDto>> viewEmployeeStatus(@RequestBody RequestDateDto requestDateDto) throws Exception{
        AuthenticateUser();
        List<EmployeeStatusDto> viewEmployeeSummary = this.employeeWorkSummary.getEmployeeStatus(requestDateDto);
        return new ResponseEntity<>(viewEmployeeSummary, HttpStatus.OK);
    }
    @ResponseExceptionHandler
    @PostMapping("/employee_workhours_count")
    public ResponseEntity<List<EmployeeDaysCountListDto>> viewEmployeeWorkHours() throws Exception{
        AuthenticateUser();
        List<EmployeeDaysCountListDto> employeeWorkDaysCount = this.employeeWorkSummary.employeeWorkDaysCount();
        return new ResponseEntity<>(employeeWorkDaysCount, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping("/employee_workhours_average")
    public ResponseEntity<List<EmployeeWorkingHoursAverageDto>> viewEmployeeWorkHoursAverage() throws Exception{
        AuthenticateUser();
        List<EmployeeWorkingHoursAverageDto> employeeWorkingHoursAverage = this.employeeWorkSummary.employeeWorkDaysAverage();
        return new ResponseEntity<>(employeeWorkingHoursAverage, HttpStatus.OK);
    }
}
