package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<ProjectTask,Integer>{
    ProjectTask getById(String id);

    List<ProjectTask> getByProject_Id(String id);
}
