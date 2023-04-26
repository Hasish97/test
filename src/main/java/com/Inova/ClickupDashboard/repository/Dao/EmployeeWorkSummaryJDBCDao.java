package com.Inova.ClickupDashboard.repository.Dao;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.entity.Member;
import com.Inova.ClickupDashboard.entity.ProjectTaskAssigner;
import com.Inova.ClickupDashboard.entity.ProjectTrackIntervel;
import com.Inova.ClickupDashboard.exception.impl.AppsException;
import com.Inova.ClickupDashboard.repository.MemberRepository;
import com.Inova.ClickupDashboard.repository.ProjectTasksTrackedIntervalsRepository;
import com.Inova.ClickupDashboard.repository.TaskRepository;
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
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class EmployeeWorkSummaryJDBCDao {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectWorkSummaryJDBCDao projectWorkSummaryJDBCDao;

    @Autowired
    ProjectTasksTrackedIntervalsRepository projectTasksTrackedIntervalsRepository;

    public List<EmployeeWorkSummaryDto> getEmployeeWorkSummaryWithinDates(RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws AppsException {


        final java.util.List<EmployeeWorkSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                    \n");
        SQL.append("    member.id,                            \n");
        SQL.append("    member.name,                          \n");
        SQL.append("    SUM(pti.tracktime)                    \n");
        SQL.append("FROM member                               \n");
        SQL.append("    LEFT JOIN                             \n");
        SQL.append("    project_track_intervel pti            \n");
        SQL.append("    ON                                    \n");
        SQL.append("    member.id = pti.member_id             \n");
        SQL.append("WHERE                                     \n");

        if(!requestEmpWorkSummaryDto.getStart_date().isEmpty()){
            SQL.append("DATE(pti.utpdateadded) >= :startDate  \n");
            params.put("startDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getStart_date()));}

        if(!requestEmpWorkSummaryDto.getEnd_date().isEmpty()){
            SQL.append(" AND DATE(pti.utpdateadded) <= :endDate \n");
            params.put("endDate",   CalendarUtil.getParsedEndDateTime(requestEmpWorkSummaryDto.getEnd_date()));}

        if(requestEmpWorkSummaryDto.getCombine_ids()) {
            if(requestEmpWorkSummaryDto.getCombine_ids_with_AND()){
                if (!requestEmpWorkSummaryDto.getMember_ids().isEmpty()) {
                SQL.append("AND member.id IN (" + QueryInBuilder.buildSQLINQuery(requestEmpWorkSummaryDto.getMember_ids()) + ")   \n");
            }}
            else {
                if (!requestEmpWorkSummaryDto.getMember_ids().isEmpty()) {
                    SQL.append("OR member.id IN (" + QueryInBuilder.buildSQLINQuery(requestEmpWorkSummaryDto.getMember_ids()) + ")   \n");
                }
            }
        }

        SQL.append("group by                                   \n");
        SQL.append("member.id                                  \n");

        if(requestEmpWorkSummaryDto.getCombine_estimate()) {
            if (!requestEmpWorkSummaryDto.getEstimate_time().isEmpty()) {
                long estimateTimeMiliSeconds = Long.parseLong(requestEmpWorkSummaryDto.getEstimate_time()) * 60 * 60 * 1000;

                if (requestEmpWorkSummaryDto.getGreater_than()) {
                    SQL.append(" HAVING SUM(pti.tracktime) > :estimateTime \n");
                }
                if (!requestEmpWorkSummaryDto.getGreater_than()) {
                    SQL.append(" HAVING SUM(pti.tracktime) < :estimateTime \n");
                }
                params.put("estimateTime", estimateTimeMiliSeconds);
            }
        }
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<EmployeeWorkSummaryDto>>() {

            @Nullable
            @Override
            public List<EmployeeWorkSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    EmployeeWorkSummaryDto employeeWorkSummaryDto = new EmployeeWorkSummaryDto();
                    employeeWorkSummaryDto.setEmp_id(rs.getInt("id"));
                    employeeWorkSummaryDto.setName(rs.getNString("name"));
                    employeeWorkSummaryDto.setTotal_work_milisoconds(rs.getString("SUM(pti.tracktime)"));
                    employeeWorkSummaryDto.setTotal_work_hours(CalendarUtil.milisocondsToHours(rs.getString("SUM(pti.tracktime)")));
                    result.add(employeeWorkSummaryDto);
                }
                return result;
            }
        });
    }

    public List<WorkSummaryDto> getEmployeeWorkSummary(RequestEmpWorkSummaryDto requestEmpWorkSummaryDto, Integer emp_id) throws AppsException {

        List<WorkSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT SUM(pti.tracktime),DATE(pti.utpdateadded),m.id                                             \n");
        SQL.append("FROM member m LEFT JOIN                              \n");
        SQL.append("    project_track_intervel pti on                    \n");
        SQL.append("        m.id = pti.member_id                         \n");
        SQL.append("LEFT JOIN project_task pt                            \n");
        SQL.append("    on pt.id = pti.project_task_id                   \n");
        SQL.append("WHERE                                                \n");

        SQL.append("DATE(pti.utpdateadded) >= :startDate                 \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getStart_date()));

        SQL.append(" AND DATE(pti.utpdateadded) <= :endDate              \n");
        params.put("endDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getEnd_date()));

        SQL.append("AND                                                  \n");

        params.put("m.id", emp_id);
        SQL.append(" m.id =:m.id                                         \n");
        SQL.append("GROUP BY DATE(pti.utpdateadded)                      \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<WorkSummaryDto>>() {

            @Nullable
            @Override
            public List<WorkSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    WorkSummaryDto workSummaryDto = new WorkSummaryDto();
                    workSummaryDto.setId(rs.getInt("id"));
                    String res = new BigDecimal(rs.getString("SUM(pti.tracktime)")).toPlainString();
                    workSummaryDto.setTime_mseconds(res);
                    workSummaryDto.setTime_hours(CalendarUtil.milisocondsToHours(res));
                    workSummaryDto.setTracked_date(rs.getString("DATE(pti.utpdateadded)"));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    try {
                        Date date = format.parse(rs.getString("DATE(pti.utpdateadded)"));
                        Format f = new SimpleDateFormat("EEEE");
                        String str = f.format(date);
                        workSummaryDto.setTracked_day(str);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    result.add(workSummaryDto);
                }
                return result;
            }
        });
    }

    public EmployeeTasksListDto getTaskListForEmployee(ProjectTaskAssigner task, Member member, RequestDateRangeDto requestDateRangeDto) throws AppsException, ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = sdformat.parse(CalendarUtil.getMysqlDateOnlyString(CalendarUtil.getParsedStartDateTime(requestDateRangeDto.getStart_date())));
        Date end_date = sdformat.parse(CalendarUtil.getMysqlDateOnlyString(CalendarUtil.getParsedStartDateTime(requestDateRangeDto.getEnd_date())));
        int full_time=0;

        EmployeeTasksListDto employeeTasksListDto=new EmployeeTasksListDto();

            for(ProjectTrackIntervel intervel:member.getProjectTrackIntervels()){
                if(intervel.getProjectTask().getName()==task.getProjectTask().getName()){
                    Date compare_date1=sdformat.parse(CalendarUtil.getMysqlDateOnlyString(intervel.getUtpdateadded()));
                    if(compare_date1.compareTo(start_date)>=0&&compare_date1.compareTo(end_date)<=0){
                     full_time= full_time+Integer.parseInt(intervel.getTracktime());}
                }
            }
            employeeTasksListDto.setSpent_time_mseconds(String.valueOf(full_time));
            employeeTasksListDto.setSpent_time_hours(CalendarUtil.milisocondsToHours(String.valueOf(full_time)));
            employeeTasksListDto.setStatus(task.getProjectTask().getStatus());
            employeeTasksListDto.setTask_id(task.getProjectTask().getId());
            employeeTasksListDto.setTask_name(task.getProjectTask().getName());

            List<WorkSummaryDto> workSlot=getTimeSLotsOfEmployee(requestDateRangeDto,task.getMember().getId(),task.getProjectTask().getId());
            List<WorkSummaryDto> workSlotsNew=new ArrayList<>();
            List<Date> dates = CalendarUtil.getDatesBetween(requestDateRangeDto.getStart_date(), requestDateRangeDto.getEnd_date());

            for (Date date:dates) {
             Boolean isDateFound=false;
                for (WorkSummaryDto wo:workSlot) {
                    if(CalendarUtil.getMysqlDateOnlyString(date).equals(wo.getTracked_date())) {
                        wo.setWorking_status("true");
                        workSlotsNew.add(wo);
                        isDateFound = true;
                        break;
                    }
                }
            if(isDateFound==false){
                WorkSummaryDto workSummaryDto1=new WorkSummaryDto();
                workSummaryDto1.setWorking_status("false");

                Format f = new SimpleDateFormat("EEEE");
                String str = f.format(date);
                workSummaryDto1.setTracked_day(str);
                workSummaryDto1.setId(member.getId());
                workSummaryDto1.setTime_hours("0");
                workSummaryDto1.setTime_mseconds("0");
                workSummaryDto1.setTracked_date(CalendarUtil.getMysqlDateOnlyString(date));
                workSlotsNew.add(workSummaryDto1);
            }
        }
            employeeTasksListDto.setTracked_times(workSlotsNew);

            return employeeTasksListDto;
    }
    public List<WorkSummaryDto> getTimeSLotsOfEmployee(RequestDateRangeDto requestEmpWorkSummaryDto, Integer emp_id,String task_id) throws AppsException {

        List<WorkSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT SUM(pti.tracktime),DATE(pti.utpdateadded),m.id                                             \n");
        SQL.append("FROM member m LEFT JOIN                              \n");
        SQL.append("    project_track_intervel pti on                    \n");
        SQL.append("        m.id = pti.member_id                         \n");
        SQL.append("LEFT JOIN project_task pt                            \n");
        SQL.append("    on pt.id = pti.project_task_id                   \n");
        SQL.append("WHERE                                                \n");

        SQL.append("DATE(pti.utpdateadded) >= :startDate                 \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getStart_date()));

        SQL.append(" AND DATE(pti.utpdateadded) <= :endDate              \n");
        params.put("endDate", CalendarUtil.getParsedStartDateTime(requestEmpWorkSummaryDto.getEnd_date()));

        SQL.append("AND                                                  \n");

        params.put("m.id", emp_id);
        SQL.append(" m.id =:m.id                                         \n");

        SQL.append("AND                                                  \n");
        params.put("pt.id", task_id);
        SQL.append(" pt.id =:pt.id                                       \n");

        SQL.append("GROUP BY DATE(pti.utpdateadded)                      \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<WorkSummaryDto>>() {

            @Nullable
            @Override
            public List<WorkSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    WorkSummaryDto workSummaryDto = new WorkSummaryDto();
                    workSummaryDto.setId(rs.getInt("id"));
                    String res = new BigDecimal(rs.getString("SUM(pti.tracktime)")).toPlainString();
                    workSummaryDto.setTime_mseconds(res);
                    workSummaryDto.setTime_hours(CalendarUtil.milisocondsToHours(res));
                    workSummaryDto.setTracked_date(rs.getString("DATE(pti.utpdateadded)"));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    try {
                        Date date = format.parse(rs.getString("DATE(pti.utpdateadded)"));
                        Format f = new SimpleDateFormat("EEEE");
                        String str = f.format(date);
                        workSummaryDto.setTracked_day(str);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    result.add(workSummaryDto);
                }
                return result;
            }
        });
    }

    public List<ViewEmployeeSummaryDto> viewEmployeeWorkSummary() {
        List<ViewEmployeeSummaryDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT m.id,                             \n");
        SQL.append("       m.name                            \n");
        SQL.append("FROM member m                            \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ViewEmployeeSummaryDto>>() {

            @Nullable
            @Override
            public List<ViewEmployeeSummaryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ViewEmployeeSummaryDto viewEmployeeSummaryDto = new ViewEmployeeSummaryDto();
                    viewEmployeeSummaryDto.setEmp_id(rs.getInt("m.id"));
                    viewEmployeeSummaryDto.setName(rs.getString("m.name"));
                    result.add(viewEmployeeSummaryDto);
                }
                return result;
            }
        });

    }

    public List<TotalWork> getTotalWork(int member_id, RequestDateRangeDto requestDateRangeDto) throws AppsException {
        List<TotalWork> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT SUM(pti.tracktime)                           \n");
        SQL.append("FROM project_track_intervel pti                     \n");

        params.put("pti.member_id",member_id);
        SQL.append("WHERE pti.member_id =:pti.member_id                 \n");
        SQL.append("AND                                                \n");

        SQL.append("DATE(pti.utpdateadded) >= :startDate                 \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestDateRangeDto.getStart_date()));

        SQL.append(" AND DATE(pti.utpdateadded) <= :endDate              \n");
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

    public List<TotalWork> getWorkForProject(Integer member_id,List<String> projectTasks,RequestDateRangeDto requestDateRangeDto) throws AppsException {

        List<TotalWork> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT SUM(pti.tracktime)                           \n");
        SQL.append("FROM project_track_intervel pti                     \n");
        SQL.append("WHERE                                                \n");
        params.put("pti.member_id",member_id);
        SQL.append("pti.member_id =:pti.member_id                 \n");
        SQL.append("AND pti.project_task_id IN (" + QueryInBuilder.buildSQLINQuery(projectTasks) + ") \n");
        SQL.append("AND DATE(pti.utpdateadded) >= :startDate                 \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestDateRangeDto.getStart_date()));

        SQL.append(" AND DATE(pti.utpdateadded) <= :endDate              \n");
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
    public List<TotalWork> getTotalWorkForEmployee(int emp_id, RequestDateDto requestDateDto) throws AppsException {
        List<TotalWork> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT SUM(pti.tracktime)                           \n");
        SQL.append("FROM project_track_intervel pti                     \n");

        params.put("pti.member_id",emp_id);
        SQL.append("WHERE pti.member_id =:pti.member_id                 \n");
        SQL.append("AND                                                 \n");

        SQL.append("DATE(pti.utpdateadded) = :startDate                 \n");
        params.put("startDate", CalendarUtil.getParsedStartDateTime(requestDateDto.getDate()));

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

    public List<HoursCountDto> getEmployeeDaysCount(int emp_id, int monthValue, int year) {

        List<HoursCountDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                                          \n");
        SQL.append("     SUM(t.tracktime)                                           \n");
        SQL.append(" from                                                           \n");
        SQL.append("     project_track_intervel t                                   \n");

        params.put("member_id",emp_id);
        SQL.append("WHERE t.member_id =:member_id                                   \n");

        SQL.append("   and                                                          \n");

        params.put("year",year);
        SQL.append("         YEAR(DATE(t.utpdateadded))=:year                       \n");

        params.put("month",monthValue);
        SQL.append("   and                                                          \n");
        SQL.append("         MONTH(t.utpdateadded)=:month                           \n");


        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<HoursCountDto>>() {
            @Nullable
            @Override
            public List<HoursCountDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    HoursCountDto daysCount = new HoursCountDto();
                    daysCount.setHours_count(rs.getInt("SUM(t.tracktime)"));
                    result.add(daysCount);
                }
                return result;
            }
        });
    }

    public List<HoursCountDto> getEmployeeHoursAverage(int monthValue, int year) {

        List<HoursCountDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                                          \n");
        SQL.append("     SUM(t.tracktime)                                           \n");
        SQL.append(" from                                                           \n");
        SQL.append("     project_track_intervel t                                   \n");

        params.put("year",year);
        SQL.append("        WHERE YEAR(DATE(t.utpdateadded))=:year                       \n");

        params.put("month",monthValue);
        SQL.append("   and                                                          \n");
        SQL.append("         MONTH(t.utpdateadded)=:month                           \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<HoursCountDto>>() {
            @Nullable
            @Override
            public List<HoursCountDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    HoursCountDto daysCount = new HoursCountDto();
                    daysCount.setHours_count(rs.getLong("SUM(t.tracktime)"));
                    result.add(daysCount);
                }
                return result;
            }
        });
    }
}

