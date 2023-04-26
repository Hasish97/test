package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.Organization;
import com.Inova.ClickupDashboard.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Integer>{

    Organization getReferenceById(String id);
}
