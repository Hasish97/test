package com.Inova.ClickupDashboard.service.impl;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.entity.Member;
import com.Inova.ClickupDashboard.entity.ProjectTask;
import com.Inova.ClickupDashboard.entity.ProjectTaskAssigner;
import com.Inova.ClickupDashboard.exception.impl.AppsException;
import com.Inova.ClickupDashboard.repository.Dao.EmployeeWorkSummaryJDBCDao;
import com.Inova.ClickupDashboard.repository.Dao.ProjectWorkSummaryJDBCDao;
import com.Inova.ClickupDashboard.repository.MemberRepository;
import com.Inova.ClickupDashboard.repository.TaskRepository;
import com.Inova.ClickupDashboard.service.EmployeeWorkSummaryService;
import com.Inova.ClickupDashboard.utils.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeWorkSummaryImpl implements EmployeeWorkSummaryService {

    @Autowired
    EmployeeWorkSummaryJDBCDao employeeWorkSummaryJDBCDao;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    Gson gson;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectWorkSummaryJDBCDao projectWorkSummaryJDBCDao;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<EmployeeWorkSummaryDto> getEmployeeWorkSummary(RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws AppsException {
        List<EmployeeWorkSummaryDto> employeeWorkSummaryDto=employeeWorkSummaryJDBCDao.getEmployeeWorkSummaryWithinDates(requestEmpWorkSummaryDto);


        List<Date> dates = CalendarUtil.getDatesBetween(requestEmpWorkSummaryDto.getStart_date(), requestEmpWorkSummaryDto.getEnd_date());
        for (EmployeeWorkSummaryDto emp:employeeWorkSummaryDto) {

            List<WorkSummaryDto> workSummaryDtos= new ArrayList<>();
            List<WorkSummaryDto> workSummaryDto=employeeWorkSummaryJDBCDao.getEmployeeWorkSummary(requestEmpWorkSummaryDto,emp.getEmp_id());

            for (Date date:dates) {
                Boolean isDateFound=false;
                for (WorkSummaryDto wo:workSummaryDto) {
                    if(CalendarUtil.getMysqlDateOnlyString(date).equals(wo.getTracked_date())) {
                        wo.setWorking_status("true");
                        workSummaryDtos.add(wo);
                        isDateFound = true;
                        break;
                    }

                }
                if(isDateFound==false){
                    WorkSummaryDto workSummaryDto1=new WorkSummaryDto();
                    Format f = new SimpleDateFormat("EEEE");
                    String str = f.format(date);
                    workSummaryDto1.setTime_mseconds("0");
                    workSummaryDto1.setTime_hours("0");
                    workSummaryDto1.setTracked_day(str);
                    workSummaryDto1.setId(emp.getEmp_id());
                    workSummaryDto1.setWorking_status("false");
                    workSummaryDto1.setTracked_date(CalendarUtil.getMysqlDateOnlyString(date));
                    workSummaryDtos.add(workSummaryDto1);
                }
            }
            emp.setWork_summary(workSummaryDtos);
        }


        return employeeWorkSummaryDto;
    }
    public List<EmployeeTasksListDto> getEmployeeTaskSummary(Integer member_id, RequestDateRangeDto requestDateRangeDto) throws AppsException, ParseException {

        Member member=this.memberRepository.getMemberById(member_id);
        List<EmployeeTasksListDto> employeeTaskList=new ArrayList<>();
        EmployeeTasksListDto taskList=new EmployeeTasksListDto();

        for (ProjectTaskAssigner task:member.getProjectTaskAssigners()) {
            taskList=this.employeeWorkSummaryJDBCDao.getTaskListForEmployee(task,member,requestDateRangeDto);
            employeeTaskList.add(taskList);
        }

        return employeeTaskList;
    }

    public List<ViewEmployeeSummaryDto> getViewEmployeeSummary(RequestDateRangeDto requestDateRangeDto) throws AppsException {
        List<ViewEmployeeSummaryDto> viewEmployeeSummaryDtos= employeeWorkSummaryJDBCDao.viewEmployeeWorkSummary();
        List<TotalWork> totalWorks=new ArrayList<>();
        for (ViewEmployeeSummaryDto dto:viewEmployeeSummaryDtos) {
           totalWorks=employeeWorkSummaryJDBCDao.getTotalWork(dto.getEmp_id(),requestDateRangeDto);
           dto.setTotalWorks(totalWorks);
        }
        return viewEmployeeSummaryDtos;
    }

    public List<EmployeeStatusDto> getEmployeeStatus(RequestDateDto requestDateDto) throws AppsException {
        List<ViewEmployeeSummaryDto> viewEmployeeStatus=employeeWorkSummaryJDBCDao.viewEmployeeWorkSummary();
        List<TotalWork> totalWorks=new ArrayList<>();
        List<EmployeeStatusDto> employeeStatusDtos=new ArrayList<>();
        for (ViewEmployeeSummaryDto dto:viewEmployeeStatus) {
            totalWorks=employeeWorkSummaryJDBCDao.getTotalWorkForEmployee(dto.getEmp_id(),requestDateDto);
            dto.setTotalWorks(totalWorks);
        }
        int inactive_users=0;
        int active_users=0;
        int partially_active_users=0;
        for (ViewEmployeeSummaryDto viewEmployeeSummaryDto:viewEmployeeStatus) {
            for (TotalWork totalWork:viewEmployeeSummaryDto.getTotalWorks()) {
                if(Long.parseLong(totalWork.getTotal_work_milisoconds())==0){
                        inactive_users++;
                }else if(Long.parseLong(totalWork.getTotal_work_milisoconds())>0&&Long.parseLong(totalWork.getTotal_work_milisoconds())<28800000){
                        partially_active_users++;
                }else{
                        active_users++;
                }
            }
        }
        EmployeeStatusDto employeeStatusDto=new EmployeeStatusDto();
        employeeStatusDto.setActive_users(active_users);
        employeeStatusDto.setInactive_users(inactive_users);
        employeeStatusDto.setPartially_active_users(partially_active_users);
        employeeStatusDto.setFull_count(active_users+inactive_users+partially_active_users);
        employeeStatusDtos.add(employeeStatusDto);
        return employeeStatusDtos;
    }

    @Override
    public List<ViewEmployeeSummaryDto> getWorkForProject(String project_id, RequestDateRangeDto requestDateRangeDto) throws AppsException {
        List<ViewEmployeeSummaryDto> viewEmployeeSummary=new ArrayList<>();
        List<ProjectTask> tasks=taskRepository.getByProject_Id(project_id);
        List<String> task_ids= new ArrayList<>();

        for (ProjectTask id:tasks) {
            task_ids.add(id.getId());
        }

            List<Assignees> assignees=this.projectWorkSummaryJDBCDao.getProjectAssignersDetails(task_ids);
            for(Assignees emp:assignees){
                ViewEmployeeSummaryDto viewEmployeeSummaryDto=new ViewEmployeeSummaryDto();
                if(emp.getId()!=0)
                {
                viewEmployeeSummaryDto.setEmp_id(emp.getId());
                viewEmployeeSummaryDto.setName(emp.getName());
                List<TotalWork> totalWorks= employeeWorkSummaryJDBCDao.getWorkForProject(emp.getId(),task_ids,requestDateRangeDto);
                viewEmployeeSummaryDto.setTotalWorks(totalWorks);
                viewEmployeeSummary.add(viewEmployeeSummaryDto);
                }
            }

        return viewEmployeeSummary;
    }

    @Override
    public List<EmployeeDaysCountListDto> employeeWorkDaysCount() throws Exception {
        LocalDate previous_month=LocalDate.now().minusMonths(1);
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String month = monthName[previous_month.getMonthValue()-1];

        List<ViewEmployeeSummaryDto> viewEmployeeSummary= employeeWorkSummaryJDBCDao.viewEmployeeWorkSummary();
        List<EmployeeWorkDaysCountDto> employeeWorkDaysCount=new ArrayList<>();
        List<HoursCountDto> daysCount=new ArrayList<>();
        List<EmployeeDaysCountListDto> employeeDaysCountList=new ArrayList<>();
        long full_hours=0;
        for (ViewEmployeeSummaryDto dto:viewEmployeeSummary) {
          EmployeeWorkDaysCountDto workDaysCountDto=new EmployeeWorkDaysCountDto();
          EmployeeDaysCountListDto employeeDaysCountLis=new EmployeeDaysCountListDto();

          daysCount=employeeWorkSummaryJDBCDao.getEmployeeDaysCount(dto.getEmp_id(),previous_month.getMonthValue(),previous_month.getYear());
            for (HoursCountDto hours:daysCount) {
                full_hours=hours.getHours_count();
            }
          workDaysCountDto.setHours_count(CalendarUtil.milisocondsToHours(String.valueOf(full_hours)));
          workDaysCountDto.setImp_id(dto.getEmp_id());
          workDaysCountDto.setImp_name(dto.getName());
          employeeWorkDaysCount.add(workDaysCountDto);

          employeeDaysCountLis.setYear(previous_month.getYear());
          employeeDaysCountLis.setMonth(previous_month.getMonthValue());
          employeeDaysCountLis.setMonth_name(month);
          employeeDaysCountLis.setEmployeeWorkHoursCount(employeeWorkDaysCount);
          employeeDaysCountList.add(employeeDaysCountLis);
        }

        return employeeDaysCountList;
    }
    public List<EmployeeWorkingHoursAverageDto> employeeWorkDaysAverage() throws Exception {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        LocalDate previous_month_one=LocalDate.now().minusMonths(1);
        String month_one = monthName[previous_month_one.getMonthValue()-1];

        LocalDate previous_month_two=LocalDate.now().minusMonths(2);
        String month_two = monthName[previous_month_one.getMonthValue()-2];

        LocalDate previous_month_third=LocalDate.now().minusMonths(3);
        String month_third = monthName[previous_month_one.getMonthValue()-3];

        List<EmployeeWorkingHoursAverageDto> employeeWorkingHoursAverageList=new ArrayList<>();
        List<HoursCountDto> daysCount=new ArrayList<>();
        long full_hours=0;
        List<ViewEmployeeSummaryDto> viewEmployeeSummary= employeeWorkSummaryJDBCDao.viewEmployeeWorkSummary();
        daysCount=employeeWorkSummaryJDBCDao.getEmployeeHoursAverage(previous_month_one.getMonthValue(),previous_month_one.getYear());
        for (HoursCountDto hours:daysCount) {
            full_hours=hours.getHours_count();
        }
        EmployeeWorkingHoursAverageDto employeeWorkingHoursAverage=new EmployeeWorkingHoursAverageDto();
        employeeWorkingHoursAverage.setAverage_working_milliseconds(String.valueOf(full_hours/viewEmployeeSummary.size()));
        employeeWorkingHoursAverage.setEmployee_count(viewEmployeeSummary.size());
        employeeWorkingHoursAverage.setAverage_working_hours(CalendarUtil.milisocondsToHours(String.valueOf(full_hours/viewEmployeeSummary.size())));
        employeeWorkingHoursAverage.setMonth(month_one);
        employeeWorkingHoursAverageList.add(employeeWorkingHoursAverage);

        daysCount=employeeWorkSummaryJDBCDao.getEmployeeHoursAverage(previous_month_two.getMonthValue(),previous_month_two.getYear());
        for (HoursCountDto hours:daysCount) {
            full_hours=hours.getHours_count();
        }
        EmployeeWorkingHoursAverageDto employeeWorkingHoursAverage_one=new EmployeeWorkingHoursAverageDto();
        employeeWorkingHoursAverage_one.setAverage_working_milliseconds(String.valueOf(full_hours/viewEmployeeSummary.size()));
        employeeWorkingHoursAverage_one.setAverage_working_hours(CalendarUtil.milisocondsToHours(String.valueOf(full_hours/viewEmployeeSummary.size())));
        employeeWorkingHoursAverage_one.setEmployee_count(viewEmployeeSummary.size());
        employeeWorkingHoursAverage_one.setMonth(month_two);
        employeeWorkingHoursAverageList.add(employeeWorkingHoursAverage_one);

        daysCount=employeeWorkSummaryJDBCDao.getEmployeeHoursAverage(previous_month_third.getMonthValue(),previous_month_third.getYear());
        for (HoursCountDto hours:daysCount) {
            full_hours=hours.getHours_count();
        }
        EmployeeWorkingHoursAverageDto employeeWorkingHoursAverage_two=new EmployeeWorkingHoursAverageDto();
        employeeWorkingHoursAverage_two.setAverage_working_milliseconds(String.valueOf(full_hours/viewEmployeeSummary.size()));
        employeeWorkingHoursAverage_two.setEmployee_count(viewEmployeeSummary.size());
        employeeWorkingHoursAverage_two.setAverage_working_hours(CalendarUtil.milisocondsToHours(String.valueOf(full_hours/viewEmployeeSummary.size())));
        employeeWorkingHoursAverage_two.setMonth(month_third);
        employeeWorkingHoursAverageList.add(employeeWorkingHoursAverage_two);

        return employeeWorkingHoursAverageList;
    }
}
