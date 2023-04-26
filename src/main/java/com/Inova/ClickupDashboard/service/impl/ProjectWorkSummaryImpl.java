package com.Inova.ClickupDashboard.service.impl;

import com.Inova.ClickupDashboard.dtos.*;
import com.Inova.ClickupDashboard.entity.Project;
import com.Inova.ClickupDashboard.entity.ProjectTask;
import com.Inova.ClickupDashboard.exception.impl.AppsException;
import com.Inova.ClickupDashboard.repository.Dao.ProjectWorkSummaryJDBCDao;
import com.Inova.ClickupDashboard.repository.ProjectRepository;
import com.Inova.ClickupDashboard.repository.ProjectTasksTrackedIntervalsRepository;
import com.Inova.ClickupDashboard.repository.TaskRepository;
import com.Inova.ClickupDashboard.service.ProjectWorkSummaryService;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectWorkSummaryImpl implements ProjectWorkSummaryService {
    @Autowired
    ProjectWorkSummaryJDBCDao projectWorkSummaryJDBCDao;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectTasksTrackedIntervalsRepository projectTasksTrackedIntervalsRepository;

    @Autowired
    ProjectRepository projectRepository;
    @Override
    public List<ProjectWorkSummaryDto> getAllProjectWorkSummary(String project_id) throws AppsException {
        return projectWorkSummaryJDBCDao.getProjectWorkSummary(project_id);
    }
    public List<ProjectWorkSummaryDto> getAllProjectWorkSummaryWithinDates(String project_id, RequestEmpWorkSummaryDto requestEmpWorkSummaryDto) throws AppsException {
        return projectWorkSummaryJDBCDao.getProjectWorkSummaryWithinDates(project_id, requestEmpWorkSummaryDto);
    }
    public List<ProjectAssignersDetailsDto> getProjectAssignersDetails(RequestDateRangeDto requestDateRangeDto) throws AppsException {
        List<Project> projects=projectRepository.findAll();
        List<ProjectAssignersDetailsDto> projectAssignersDetails=new ArrayList<>();

        for (Project p:projects) {
            ProjectAssignersDetailsDto projectAssignerDtails=new ProjectAssignersDetailsDto();
            projectAssignerDtails.setProject_id(p.getId());
            projectAssignerDtails.setProject_name(p.getName());
            projectAssignerDtails.setCheckPointsMstData(projectWorkSummaryJDBCDao.getProjectMasterData(p.getId()));
            List<ProjectTask> tasks=taskRepository.getByProject_Id(p.getId());

            List<String> task_ids= new ArrayList<>();
            for (ProjectTask id:tasks) {
                task_ids.add(id.getId());
            }
            List<TotalWork> totalWorks=projectWorkSummaryJDBCDao.getTotalWorkForProject(task_ids,requestDateRangeDto);
            projectAssignerDtails.setTotalWorks(totalWorks);
            projectAssignerDtails.setStatus("NA");
            projectAssignerDtails.setAssignees(projectWorkSummaryJDBCDao.getProjectAssignersDetails(task_ids));
            projectAssignersDetails.add(projectAssignerDtails);
        }
        return projectAssignersDetails;
    }

    @Override
    public List<ProjectStatusSummaryCountDto> getProjectSummaryCount() throws AppsException {
        List<ProjectStatusSummaryDto> projectStatusSummary=projectWorkSummaryJDBCDao.getProjectStatus();
        int completed=0;
        int pending=0;
        for (ProjectStatusSummaryDto projectStatusSummaryDto:projectStatusSummary) {
            if(projectStatusSummaryDto.getStatus().contains("done")){
                ++completed;
            }
            else {
                ++pending;
            }
        }
        ProjectStatusSummaryCountDto projectStatusSummaryCountDto=new ProjectStatusSummaryCountDto();
        List<ProjectStatusSummaryCountDto> projectStatusSummaryCount=new ArrayList<>();
        projectStatusSummaryCountDto.setCompleted(completed);
        projectStatusSummaryCountDto.setPending(pending);
        projectStatusSummaryCount.add(projectStatusSummaryCountDto);
        return projectStatusSummaryCount;
    }

}
