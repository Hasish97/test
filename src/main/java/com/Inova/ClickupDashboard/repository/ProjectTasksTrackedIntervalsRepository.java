package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.Member;
import com.Inova.ClickupDashboard.entity.ProjectTask;
import com.Inova.ClickupDashboard.entity.ProjectTrackIntervel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProjectTasksTrackedIntervalsRepository extends JpaRepository<ProjectTrackIntervel,Integer>{
}
