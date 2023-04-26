package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectFolders;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Con_Categories {
    private String id;
    private String name;
    private String project_id;
    ArrayList<Con_FolderSharedMembers> members;
    ArrayList<Con_Subcategories> subcategories;
}
