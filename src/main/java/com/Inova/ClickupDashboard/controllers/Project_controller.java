package com.Inova.ClickupDashboard.controllers;


import com.Inova.ClickupDashboard.dtos.ProjectDto;
import com.Inova.ClickupDashboard.entity.MetProjectCheckPoint;
import com.Inova.ClickupDashboard.exception.aop.ResponseExceptionHandler;
import com.Inova.ClickupDashboard.dtos.ProjectCheckPointsDto;
import com.Inova.ClickupDashboard.dtos.ProjectIdDto;
import com.Inova.ClickupDashboard.dtos.ProjectMasterDataDto;
import com.Inova.ClickupDashboard.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Inova.ClickupDashboard.service.UserAuthentication.AuthenticateUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Project_controller {

    private  final ProjectService projectService;

    @ResponseExceptionHandler
    @PostMapping("/create_mst_data")
    public ResponseEntity<ProjectMasterDataDto> saveProject(@RequestBody ProjectMasterDataDto project) throws Exception{
        AuthenticateUser();
        return  new ResponseEntity<ProjectMasterDataDto>(projectService.saveProject(project), HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping("/get_project_masterData")
    public ResponseEntity<List<ProjectMasterDataDto>> viewProjectMasterData(@RequestBody ProjectIdDto projectId) throws Exception {
        AuthenticateUser();
        List<ProjectMasterDataDto> projectMasterDataDto = this.projectService.viewProjectMasterData(projectId.getProject_id());
        return new ResponseEntity<>(projectMasterDataDto, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PutMapping("/update_project_masterData")
    public ResponseEntity<List<ProjectMasterDataDto>> updateProjectMasterData(@RequestBody ProjectMasterDataDto project) throws Exception {
        AuthenticateUser();
        ProjectMasterDataDto projectMasterDataDto = this.projectService.updateProjectMasterData(project);
        return new ResponseEntity(projectMasterDataDto, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping("/get_project_checkpoints/{project_Id}")
    public ResponseEntity<List<MetProjectCheckPoint>> getProjectCheckPoints(@PathVariable ProjectIdDto project_Id) throws Exception{
        AuthenticateUser();
        List<ProjectCheckPointsDto> metProjectCheckPoints = this.projectService.getProjectCheckpointsPercentages(project_Id.getProject_id());
        return new ResponseEntity(metProjectCheckPoints, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping("/get_selected_project/{project_Id}")
    public ResponseEntity <ProjectDto> viewProject(@PathVariable ProjectIdDto project_Id) throws Exception {
        ProjectDto projectDto =this.projectService.viewProject(project_Id.getProject_id());
        return new ResponseEntity(projectDto, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping("/get_all_projects")
    public ResponseEntity <List<ProjectDto>> viewAllProjects() throws Exception {
        List<ProjectDto> projectDto =this.projectService.viewAllProjects();
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }
}
