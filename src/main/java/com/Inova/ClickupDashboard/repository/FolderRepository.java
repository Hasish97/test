package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.ProjectFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<ProjectFolder,Integer>{
    ProjectFolder getProjectFolderById(String id);

}
