package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectSubTasks;

import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectLIstTasks.Con_Assignees;
import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectLIstTasks.Con_Status;
import lombok.Data;

import java.util.ArrayList;


@Data
public class Con_subtasks {
    private String id;
    private Con_Status status;
    private Integer subtasks_count;
    private String parent;
    private String name;
    private String date_created;
    private String due_date;
    private String time_spent;
    ArrayList<Con_Assignees> assignees;
}
