package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.ProjectList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectListRepository extends JpaRepository<ProjectList,Integer>{
    ProjectList getById(String id);

}
