package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.MetProjectCheckPoint;
import com.Inova.ClickupDashboard.entity.MetProjectFinaicialCheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectFinancialCheckPointsRepository extends JpaRepository<MetProjectFinaicialCheckPoint,Integer>{
     List<MetProjectFinaicialCheckPoint> getMetProjectFinaicialCheckPointByProjectId(String project_id);

}
