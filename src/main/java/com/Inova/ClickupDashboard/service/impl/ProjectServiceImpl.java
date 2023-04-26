package com.Inova.ClickupDashboard.service.impl;

import com.Inova.ClickupDashboard.dtos.ProjectDto;
import com.Inova.ClickupDashboard.entity.MetProjectCheckPoint;
import com.Inova.ClickupDashboard.entity.MetProjectFinaicialCheckPoint;
import com.Inova.ClickupDashboard.entity.MstProjectDetail;
import com.Inova.ClickupDashboard.dtos.ProjectCheckPointsDto;
import com.Inova.ClickupDashboard.dtos.ProjectMasterDataDto;
import com.Inova.ClickupDashboard.entity.Project;
import com.Inova.ClickupDashboard.exception.impl.AppsException;
import com.Inova.ClickupDashboard.exception.impl.ClickupErrorCOde;
import com.Inova.ClickupDashboard.repository.*;
import com.Inova.ClickupDashboard.repository.Dao.ProjectMstDataJDBCDao;
import com.Inova.ClickupDashboard.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private  final ProjectRepository projectRepository;
    private final ProjectMstDetailRepository projectMstDetailRepository;
    private final ProjectMstDataJDBCDao projectMstDataJDBCDao;
    private final ProjectCheckPointsRepository projectCheckPointsRepository;
    private final ProjectFinancialCheckPointsRepository projectFinancialCheckPointsRepository;

    private final ProjectMasterDetailsRepository projectMasterDetailsRepository;

    @Override
    public ProjectMasterDataDto saveProject(ProjectMasterDataDto project) throws AppsException {
        Date date=new Date();
        if(projectRepository.getProjectById(project.getProject_id())==null){
            throw new AppsException(ClickupErrorCOde.APPS_EXCEPTION_PROJECT_DOES_NOT_EXIST);
        }
        if(projectMasterDetailsRepository.getByProjectId(project.getProject_id())!=null){
            throw new AppsException(ClickupErrorCOde.APPS_EXCEPTION_MASTER_DATA_ALREADY_ADDED);
        }
        MstProjectDetail mstProjectdetail=new MstProjectDetail();
        mstProjectdetail.setMstTotalEffortSpent(project.getMstTotalEffortSpent());
        mstProjectdetail.setMstQuotedEffort(project.getMstQuotedEffort());
        mstProjectdetail.setMstQuotedManDayRate(project.getMstQuotedManDayRate());
        mstProjectdetail.setMstQuatationvalueCalulated(project.getMstQuatationvalueCalulated());
        mstProjectdetail.setMstQuatationvalueActualShared(project.getMstQuatationvalueActualShared());
        mstProjectdetail.setmstStandardManDayRate(project.getMstStandardManDayRate());
        mstProjectdetail.setProject(this.projectRepository.getProjectById(project.getProject_id()));
        mstProjectdetail.setModifiedDate(date);

        mstProjectdetail= projectMstDetailRepository.saveAndFlush(mstProjectdetail);
        addProjectCheckPoints(project);
        return new ProjectMasterDataDto(mstProjectdetail);

    }

    @Override
    public ProjectDto viewProject(String project_id) {
        Project project= projectRepository.getProjectById(project_id);
        ProjectDto projectDto =new ProjectDto();
        projectDto.setProject_Id(project.getId());
        projectDto.setName(project.getName());
        return projectDto;
    }

    @Override
    public List<ProjectDto> viewAllProjects() {
        return projectMstDataJDBCDao.getAllProjects();
    }

    @Override
    public List<ProjectCheckPointsDto> getProjectCheckpointsPercentages(String project_id) {
        return projectMstDataJDBCDao.getProjectCheckPoints(project_id);
    }

    public void addProjectCheckPoints(ProjectMasterDataDto projectMasterDataDto){
        if(!projectCheckPointsRepository.getMetProjectCheckPointByProjectId(projectMasterDataDto.getProject_id()).isEmpty()&&!projectFinancialCheckPointsRepository.getMetProjectFinaicialCheckPointByProjectId(projectMasterDataDto.getProject_id()).isEmpty()){
            List<MetProjectCheckPoint> projectCheckPoint=projectCheckPointsRepository.getMetProjectCheckPointByProjectId(projectMasterDataDto.getProject_id());
            List<MetProjectFinaicialCheckPoint> finaicialCheckPoints=projectFinancialCheckPointsRepository.getMetProjectFinaicialCheckPointByProjectId(projectMasterDataDto.getProject_id());
            for(MetProjectCheckPoint projectCheckPoint2:projectCheckPoint){
                projectCheckPoint2.setMetDerivePercentage((projectMasterDataDto.getMstTotalEffortSpent()/(projectMasterDataDto.getMstQuotedEffort()*projectCheckPoint2.getMetPercentage()/100))*100);
                projectCheckPointsRepository.saveAndFlush(projectCheckPoint2);
            }
            for(MetProjectFinaicialCheckPoint projectFinaicialCheckPoint:finaicialCheckPoints){
                projectFinaicialCheckPoint.setMetDerivePercentage(projectMasterDataDto.getMstTotalEffortSpent()/((projectMasterDataDto.getMstQuatationvalueActualShared()/projectMasterDataDto.getMstStandardManDayRate())*projectFinaicialCheckPoint.getMetPercentage()/100));
                projectFinancialCheckPointsRepository.saveAndFlush(projectFinaicialCheckPoint);
            }
            }else {
            int i=10;
            while (i <= 90) //Last number before 100 is 90
            {
                MetProjectCheckPoint projectCheckPoint=new MetProjectCheckPoint();
                MetProjectFinaicialCheckPoint metProjectFinaicialCheckPoint=new MetProjectFinaicialCheckPoint();

                projectCheckPoint.setMetPercentage(i);
                projectCheckPoint.setMetDerivePercentage((projectMasterDataDto.getMstTotalEffortSpent()/(projectMasterDataDto.getMstQuotedEffort()*i/100))*100);
                projectCheckPoint.setProject(projectRepository.getProjectById(projectMasterDataDto.getProject_id()));
                projectCheckPointsRepository.saveAndFlush(projectCheckPoint);

                metProjectFinaicialCheckPoint.setMetPercentage(i);
                metProjectFinaicialCheckPoint.setMetDerivePercentage(projectMasterDataDto.getMstTotalEffortSpent()/((projectMasterDataDto.getMstQuatationvalueActualShared()/projectMasterDataDto.getMstStandardManDayRate())*i/100)*100);
                metProjectFinaicialCheckPoint.setProject(projectRepository.getProjectById(projectMasterDataDto.getProject_id()));
                projectFinancialCheckPointsRepository.saveAndFlush(metProjectFinaicialCheckPoint);
                i=i+10;

            }
        }
    }

    @Override
    public List<ProjectMasterDataDto> viewProjectMasterData(String projectId) throws AppsException {
        return  projectMstDataJDBCDao.getProjectMasterData(projectId);
    }

    @Override
    public ProjectMasterDataDto updateProjectMasterData(ProjectMasterDataDto project) {
        addProjectCheckPoints(project);
        return projectMstDataJDBCDao.updateProjectMasterData(project);
    }
}
