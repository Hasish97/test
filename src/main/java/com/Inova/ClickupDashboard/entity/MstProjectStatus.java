package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class MstProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int projectStatusId;

    private String projectId;

    @ManyToOne
    private ProjectStatusDetail projectStatusDetail;

    public MstProjectStatus(){

    }

    public int getProjectStatusId() {
        return projectStatusId;
    }

    public void setProjectStatusId(int projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ProjectStatusDetail getProjectStatusDetail() {
        return projectStatusDetail;
    }

    public void setProjectStatusDetail(ProjectStatusDetail projectStatusDetail) {
        this.projectStatusDetail = projectStatusDetail;
    }
}
