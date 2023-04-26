package com.Inova.ClickupDashboard.dtos.ForConOperations.ProjectMembers;

import lombok.Data;

@Data
public class Con_MemberP {
    private Integer organization_id=37287189;
    private Con_User user;

    @Override
    public String toString() {
        return "Con_MemberP{" +
                "organization_id=" + organization_id +
                ", conUser=" + user +
                '}';
    }
}
