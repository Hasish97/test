package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectLIstTasks;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Con_Tasks {
    private String id;
    private String name;
    private String date_created;
    private String time_spent;
    private Integer time_estimate;
    private String due_date;
    private Con_Status status;
    private String parent;
    private Con_space space;
    private Con_list list;
    ArrayList<Con_Assignees> assignees;

}
