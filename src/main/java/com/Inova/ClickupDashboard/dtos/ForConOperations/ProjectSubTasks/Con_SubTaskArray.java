package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectSubTasks;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Con_SubTaskArray {
    private String project_id;
    private ArrayList<Con_subtasks> subtasks;
}

