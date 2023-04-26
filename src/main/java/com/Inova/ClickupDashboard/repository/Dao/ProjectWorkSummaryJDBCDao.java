package com.Inova.ClickupDashboard.repository.Dao;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.exception.impl.AppsException;
import com.Inova.ClickupDashboard.utils.CalendarUtil;
import com.Inova.ClickupDashboard.utils.QueryInBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
public class ProjectWorkSummaryJDBCDao {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ProjectWorkSummaryDto> getProjectWorkSummary(String projectId) throws AppsException {
        final java.util.List<ProjectWorkSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT SUM(t.tracktime),                                                     \n");
        SQL.append(" t.project_task_id,p.name,p.status,p.project_id                               \n");
        SQL.append(" FROM `project_track_intervel` t                                              \n");
        SQL.append(" LEFT JOIN `project_task` p ON                                                \n");
        SQL.append(" t.project_task_id=p.id WHERE                                                 \n");
        if (!projectId.equals(null)) {
            params.put("project_id", projectId);
            SQL.append("p.project_id =:project_id                                                 \n");
        }
        SQL.append("GROUP BY t.project_task_id;                                                   \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectWorkSummaryDto>>() {

            @Nullable
            @Override
            public List<ProjectWorkSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ProjectWorkSummaryDto projectWorkSummaryDto = new ProjectWorkSummaryDto();
                    projectWorkSummaryDto.setStatus(rs.getNString("status"));
                    projectWorkSummaryDto.setTask_name(rs.getNString("name"));
                    projectWorkSummaryDto.setProject_Id(rs.getString("project_id"));
                    projectWorkSummaryDto.setTask_Id(rs.getString("project_task_id"));
                    String res = new BigDecimal(rs.getString("SUM(t.tracktime)")).toPlainString();
                    projectWorkSummaryDto.setWorking_mseconds(res);
                    projectWorkSummaryDto.setWorking_hours(CalendarUtil.milisocondsToHours(res));
                    result.add(projectWorkSummaryDto);
                }
                return result;
            }
        });
    }
    public List<ProjectWorkSummaryDto> getProjectWorkSummaryWithinDates(String project_id, RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws AppsException {

        final java.util.List<ProjectWorkSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("  SELECT SUM(t.tracktime),                                              \n");
        SQL.append("  t.project_task_id,p.name,p.status,p.project_id                        \n");
        SQL.append("  FROM `project_track_intervel` t                                       \n");
        SQL.append("  LEFT JOIN `project_task` p ON                                         \n");
        SQL.append("  t.project_task_id=p.id WHERE                                          \n");
        if (!project_id.equals(null)) {
            params.put("project_id", project_id);
            SQL.append(" p.project_id =:project_id                                          \n");
        }
        SQL.append(" AND DATE(t.utpdateadded) >= :startDate                                 \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getStart_date()));

        SQL.append(" AND DATE(t.utpdateadded) <= :endDate                                   \n");
        params.put("endDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getEnd_date()));

        SQL.append(" GROUP BY t.project_task_id                                               ");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectWorkSummaryDto>>() {

            @Nullable
            @Override
            public List<ProjectWorkSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ProjectWorkSummaryDto projectWorkSummaryDto = new ProjectWorkSummaryDto();
                    projectWorkSummaryDto.setStatus(rs.getNString("status"));
                    projectWorkSummaryDto.setTask_name(rs.getNString("name"));
                    projectWorkSummaryDto.setProject_Id(rs.getString("project_id"));
                    projectWorkSummaryDto.setTask_Id(rs.getString("project_task_id"));
                    String res = new BigDecimal(rs.getString("SUM(t.tracktime)")).toPlainString();
                    projectWorkSummaryDto.setWorking_mseconds(res);
                    projectWorkSummaryDto.setWorking_hours(CalendarUtil.milisocondsToHours(res));
                    result.add(projectWorkSummaryDto);
                }
                return result;
            }
        });
    }

    public List<Assignees> getProjectAssignersDetails(List<String> projectTasks) {
        final java.util.List<Assignees> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT(t.member_id),m.name                                             \n");
        SQL.append("FROM `project_task_assigner` t                                                  \n");
        SQL.append("         left outer join member m on m.id = t.member_id                         \n");
        SQL.append("WHERE t.project_task_id IN (" + QueryInBuilder.buildSQLINQuery(projectTasks) + ")                           \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<Assignees>>() {

            @Nullable
            @Override
            public List<Assignees> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    Assignees assignees = new Assignees();
                    assignees.setId(rs.getInt("t.member_id"));
                    assignees.setName(rs.getString("m.name"));
                    result.add(assignees);
                }
                return result;
            }
        });
    }

    public List<TotalWork> getTotalWorkForProject(List<String> task, RequestDateRangeDto requestDateRangeDto) throws AppsException {
        List<TotalWork> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT SUM(pti.tracktime)                           \n");
        SQL.append("FROM project_track_intervel pti                     \n");
        SQL.append("WHERE pti.project_task_id IN (" + QueryInBuilder.buildSQLINQuery(task) + ") \n");
        SQL.append("AND                                                \n");
        SQL.append("DATE(pti.utpdateadded) >= :startDate               \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestDateRangeDto.getStart_date()));

        SQL.append(" AND DATE(pti.utpdateadded) <= :endDate            \n");
        params.put("endDate", CalendarUtil.getParsedStartDateTime(requestDateRangeDto.getEnd_date()));
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<TotalWork>>() {

            @Nullable
            @Override
            public List<TotalWork> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    TotalWork totalWork = new TotalWork();
                    if(rs.getString("SUM(pti.tracktime)")!=null){
                        totalWork.setTotal_work_milisoconds(rs.getString("SUM(pti.tracktime)"));
                        totalWork.setTotal_work_hours(CalendarUtil.milisocondsToHours(rs.getString("SUM(pti.tracktime)")));}
                    result.add(totalWork);
                }
                return result;
            }
        });
    }

    public List<ProjectMasterDataDto> getProjectMasterData(String projectId){
        final List<ProjectMasterDataDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                               \n");
        SQL.append("FROM mst_project_details                                                                        \n");
        if (!projectId.equals(null)) {
            params.put("project_id", projectId);
            SQL.append("WHERE mst_project_details.project_id =:project_id                                             ");
        }
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectMasterDataDto>>() {

            @Nullable
            @Override
            public List<ProjectMasterDataDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ProjectMasterDataDto projectMasterDataDto = new ProjectMasterDataDto();
                    projectMasterDataDto.setId(rs.getInt("id"));
                    projectMasterDataDto.setMstQuotedManDayRate(rs.getFloat("mst_quoted_man_day_rate"));
                    projectMasterDataDto.setMstQuotedEffort(rs.getFloat("mst_quoted_effort"));
                    projectMasterDataDto.setMstTotalEffortSpent(rs.getFloat("mst_total_effort_spent"));
                    projectMasterDataDto.setMstQuatationvalueActualShared(rs.getFloat("mst_quatationvalue_actual_shared"));
                    projectMasterDataDto.setMstQuatationvalueCalulated(rs.getFloat("mst_quatationvalue_calulated"));
                    projectMasterDataDto.setMstStandardManDayRate(rs.getFloat("mst_standard_man_day_rate"));
                    projectMasterDataDto.setProject_id(rs.getNString("project_id"));
                    result.add(projectMasterDataDto);
                }
                return result;
            }
        });
    }

    public List<ProjectStatusSummaryDto> getProjectStatus() {
        final List<ProjectStatusSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                  \n");
        SQL.append("from                                                                      \n");
        SQL.append("    mst_project_status m                                                  \n");
        SQL.append("        left join                                                         \n");
        SQL.append("        project_status_detail psd                                         \n");
        SQL.append("            on                                                            \n");
        SQL.append("            psd.status_id = m.project_status_detail_status_id             \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectStatusSummaryDto>>() {
            @Nullable
            @Override
            public List<ProjectStatusSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ProjectStatusSummaryDto projectMasterDataDto = new ProjectStatusSummaryDto();
                    projectMasterDataDto.setProject_id(rs.getString("project_id"));
                    projectMasterDataDto.setStatus(rs.getString("status"));
                    result.add(projectMasterDataDto);
                }
                return result;
            }
        });
    }

}
