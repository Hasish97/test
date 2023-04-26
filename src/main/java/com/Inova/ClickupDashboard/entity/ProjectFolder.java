package com.Inova.ClickupDashboard.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project_folder database table.
 * 
 */
@Entity
@Table(name="project_folder")
@NamedQuery(name="ProjectFolder.findAll", query="SELECT p FROM ProjectFolder p")
public class ProjectFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	//bi-directional many-to-one association to ProjectFolderSharedmember
	@OneToMany(mappedBy="projectFolder")
	private List<ProjectFolderSharedmember> projectFolderSharedmembers;

	//bi-directional many-to-one association to ProjectList
	@OneToMany(mappedBy="projectFolder")
	private List<ProjectList> projectLists;

	public ProjectFolder() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ProjectFolderSharedmember> getProjectFolderSharedmembers() {
		if(projectFolderSharedmembers==null){
			this.projectFolderSharedmembers=new ArrayList<>();
		}
		return this.projectFolderSharedmembers;
	}

	public void setProjectFolderSharedmembers(List<ProjectFolderSharedmember> projectFolderSharedmembers) {
		this.projectFolderSharedmembers = projectFolderSharedmembers;
	}

	public ProjectFolderSharedmember addProjectFolderSharedmember(ProjectFolderSharedmember projectFolderSharedmember) {
		getProjectFolderSharedmembers().add(projectFolderSharedmember);
		projectFolderSharedmember.setProjectFolder(this);

		return projectFolderSharedmember;
	}

	public ProjectFolderSharedmember removeProjectFolderSharedmember(ProjectFolderSharedmember projectFolderSharedmember) {
		getProjectFolderSharedmembers().remove(projectFolderSharedmember);
		projectFolderSharedmember.setProjectFolder(null);

		return projectFolderSharedmember;
	}

	public List<ProjectList> getProjectLists() {
		if(projectLists==null){
			this.projectLists=new ArrayList<>();
		}
		return this.projectLists;
	}

	public void setProjectLists(List<ProjectList> projectLists) {
		this.projectLists = projectLists;
	}

	public ProjectList addProjectList(ProjectList projectList) {
		getProjectLists().add(projectList);
		projectList.setProjectFolder(this);

		return projectList;
	}

	public ProjectList removeProjectList(ProjectList projectList) {
		getProjectLists().remove(projectList);
		projectList.setProjectFolder(null);

		return projectList;
	}

}