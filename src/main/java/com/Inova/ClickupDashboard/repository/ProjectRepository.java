package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer>{
    Project getProjectById(String project_id);
}
