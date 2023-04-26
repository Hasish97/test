package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers;

import lombok.Data;

import java.io.Serializable;

@Data
public class Con_Projects implements Serializable {
    private String id;
    private String date_created;
    private String name;
    private Con_MainTeam team;

    @Override
    public String toString() {
        return "Con_Projects{" +
                "id='" + id + '\'' +
                ", date_created='" + date_created + '\'' +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}
