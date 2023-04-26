package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectFolders;


import com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers.Con_User;
import lombok.Data;

@Data
public class Con_FolderSharedMembers {
    private Con_User user;

    @Override
    public String toString() {
        return "Con_FolderSharedMembers{" +
                "conUser=" + user +
                '}';
    }
}
