package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
public class ProjectStatusDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int statusId;

    private String status;

    @OneToMany(mappedBy="projectStatusDetail")
    private List<MstProjectStatus> mstProjectStatuses;

    public ProjectStatusDetail(){

    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MstProjectStatus> getMstProjectStatuses() {
        return mstProjectStatuses;
    }

    public void setMstProjectStatuses(List<MstProjectStatus> mstProjectStatuses) {
        this.mstProjectStatuses = mstProjectStatuses;
    }
}
