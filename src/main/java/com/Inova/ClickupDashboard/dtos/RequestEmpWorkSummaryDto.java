package com.Inova.ClickupDashboard.dtos;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestEmpWorkSummaryDto implements Serializable {
    private String start_date;
    private String end_date;
    private List<Integer> member_ids;
    private String estimate_time;
    private Boolean greater_than;
    private Boolean combine_ids;
    private Boolean combine_ids_with_AND;
    private Boolean combine_estimate;
}
