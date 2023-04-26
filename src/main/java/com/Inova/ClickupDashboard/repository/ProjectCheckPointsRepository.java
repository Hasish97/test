package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.MetProjectCheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCheckPointsRepository extends JpaRepository<MetProjectCheckPoint,Integer>{
     List<MetProjectCheckPoint> getMetProjectCheckPointByProjectId(String project_id);

}
