package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Con_Team {
    private Integer id;
    private String name;
    ArrayList<Con_Projects> projects;
    ArrayList<Con_MemberP> members;

    @Override
    public String toString() {
        return "Con_Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projects=" + projects +
                ", members=" + members +
                '}';
    }
}
