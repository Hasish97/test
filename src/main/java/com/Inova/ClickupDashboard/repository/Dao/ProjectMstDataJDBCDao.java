package com.Inova.ClickupDashboard.repository.Dao;

import com.Inova.ClickupDashboard.dtos.ProjectDto;
import com.Inova.ClickupDashboard.entity.MstProjectDetail;
import com.Inova.ClickupDashboard.dtos.ProjectCheckPointsDto;
import com.Inova.ClickupDashboard.dtos.ProjectMasterDataDto;
import com.Inova.ClickupDashboard.repository.ProjectMstDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectMstDataJDBCDao {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ProjectMstDetailRepository projectMstDetailRepository;
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
    public List<ProjectCheckPointsDto> getProjectCheckPoints(String projectId){
        final List<ProjectCheckPointsDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("  SELECT *                                                                 \n");
        SQL.append("  FROM met_project_check_point                                             \n");
        if (!projectId.equals(null)) {
            params.put("project_id", projectId);
            SQL.append("WHERE met_project_check_point.project_id =:project_id                  ");
        }
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectCheckPointsDto>>() {

            @Nullable
            @Override
            public List<ProjectCheckPointsDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ProjectCheckPointsDto projectMasterDataDto = new ProjectCheckPointsDto();
                    projectMasterDataDto.setDerived_checkpoint_percentage(rs.getFloat("met_derive_percentage"));
                    projectMasterDataDto.setGiven_percentage(rs.getFloat("met_percentage"));
                    result.add(projectMasterDataDto);
                }
                return result;
            }
        });
    }
    public ProjectMasterDataDto updateProjectMasterData(ProjectMasterDataDto projectMasterDataDto) {

        MstProjectDetail mstProjectdetail= projectMstDetailRepository.getByProject_Id(projectMasterDataDto.getProject_id());

        mstProjectdetail.setMstQuotedEffort(projectMasterDataDto.getMstQuotedEffort());
        mstProjectdetail.setMstQuatationvalueCalulated(projectMasterDataDto.getMstQuatationvalueCalulated());
        mstProjectdetail.setMstQuatationvalueActualShared(projectMasterDataDto.getMstQuatationvalueActualShared());
        mstProjectdetail.setMstQuotedManDayRate(projectMasterDataDto.getMstQuotedManDayRate());
        mstProjectdetail.setMstTotalEffortSpent(projectMasterDataDto.getMstTotalEffortSpent());
        mstProjectdetail.setmstStandardManDayRate(projectMasterDataDto.getMstStandardManDayRate());
        mstProjectdetail= projectMstDetailRepository.save(mstProjectdetail);

        return new ProjectMasterDataDto(mstProjectdetail);
    }

    public List<ProjectDto> getAllProjects() {
        final List<ProjectDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT *                                                   \n");
        SQL.append(" FROM project                                                 ");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ProjectDto>>() {
            @Nullable
            @Override
            public List<ProjectDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ProjectDto projectDto = new ProjectDto();
                    projectDto.setProject_Id(rs.getNString("id"));
                    projectDto.setName(rs.getString("name"));
                    result.add(projectDto);
                }
                return result;
            }
        });
    }
}
