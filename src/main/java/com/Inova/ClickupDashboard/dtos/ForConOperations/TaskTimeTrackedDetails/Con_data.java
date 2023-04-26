package com.Inova.ClickupDashboard.dtos.ForConOperations.TaskTimeTrackedDetails;

import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers.Con_User;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Con_data {
    private Con_User user;
    private String time;
    private ArrayList<Con_Interval> intervals;
}
