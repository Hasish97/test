package com.Inova.ClickupDashboard.controllers.Con_operation_controller;


import com.Inova.ClickupDashboard.controllers.App_constants;
import com.Inova.ClickupDashboard.entity.Project;
import com.Inova.ClickupDashboard.entity.ProjectList;
import com.Inova.ClickupDashboard.entity.ProjectTask;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers.Con_ProjectMembers;
import com.Inova.ClickupDashboard.service.ConOperationService.ConOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class Con_operations {
    @Autowired
    ConOperations conOperations;
    @Scheduled(fixedDelay = App_constants.THREAD_TIME)
    public Con_ProjectMembers getProjectsAndOrganizationMembers() throws IOException, InterruptedException {
        return conOperations.saveProjectsAndMembers();}
    @Scheduled(fixedDelay = App_constants.THREAD_TIME)
    public List<Project> projectFolder(){
        return conOperations.getProjectFoldersAndList();
    }
    @Scheduled(fixedDelay = App_constants.THREAD_TIME)
    public List<ProjectList> projectListTasks(){
        return conOperations.getProjectListTasksAndAssigners();
    }
    @Scheduled(fixedDelay = App_constants.THREAD_TIME)
    public List<ProjectTask> projectTimeIntervalTracked(){
        return conOperations.getTrackedTimeIntervalsForTasks();
    }
    @Scheduled(fixedDelay = App_constants.THREAD_TIME)
    public List<ProjectTask> getSubtask(){
        return conOperations.getSubtasksForMainTasks();
    }
}

//cron = "${com.scheduled.cron3}"
