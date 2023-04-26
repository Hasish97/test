package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.MstProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMstDetailRepository extends JpaRepository<MstProjectDetail,Integer>{
    MstProjectDetail getByProject_Id(String id);


}
