package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.Member;
import com.Inova.ClickupDashboard.entity.Project;
import com.Inova.ClickupDashboard.entity.ProjectTask;
import com.Inova.ClickupDashboard.entity.ProjectTaskAssigner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTasksAssignerRepository extends JpaRepository<ProjectTaskAssigner,Integer>{
    ProjectTaskAssigner getProjectTaskAssignerByProjectTaskAndMember(ProjectTask projectTask, Member member);
}
