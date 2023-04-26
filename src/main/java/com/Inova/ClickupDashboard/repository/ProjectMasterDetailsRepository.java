package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.MstProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMasterDetailsRepository extends JpaRepository<MstProjectDetail,Integer>{
        MstProjectDetail getByProjectId(String project_id);
}
