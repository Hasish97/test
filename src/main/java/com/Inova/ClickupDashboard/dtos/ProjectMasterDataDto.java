package com.Inova.ClickupDashboard.dtos;

import com.Inova.ClickupDashboard.entity.MstProjectDetail;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectMasterDataDto implements Serializable {
    private  Integer id;
    private Float mstQuatationvalueActualShared;
    private Float mstQuatationvalueCalulated;
    private Float mstQuotedEffort;
    private Float mstQuotedManDayRate;
    private Float mstTotalEffortSpent;
    private Float mstStandardManDayRate;

    private String project_id;

    public ProjectMasterDataDto(){}

    public ProjectMasterDataDto(MstProjectDetail project){
        this.id = project.getId();
        this.mstQuatationvalueCalulated =project.getMstQuatationvalueCalulated();
        this.mstQuatationvalueActualShared =project.getMstQuatationvalueActualShared();
        this.mstQuotedManDayRate =project.getMstQuotedManDayRate();
        this.mstQuotedEffort =project.getMstQuotedEffort();
        this.mstTotalEffortSpent =project.getMstTotalEffortSpent();
        this.mstStandardManDayRate =project.getmstStandardManDayRate();
    }
}
