package com.Inova.ClickupDashboard.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project_list database table.
 * 
 */
@Entity
@Table(name="project_list")
@NamedQuery(name="ProjectList.findAll", query="SELECT p FROM ProjectList p")
public class ProjectList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	//bi-directional many-to-one association to ProjectFolder
	@ManyToOne
	private ProjectFolder projectFolder;

	//bi-directional many-to-one association to ProjectTask
	@OneToMany(mappedBy="projectList")
	private List<ProjectTask> projectTasks;

	public ProjectList() {
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

	public ProjectFolder getProjectFolder() {
		return this.projectFolder;
	}

	public void setProjectFolder(ProjectFolder projectFolder) {
		this.projectFolder = projectFolder;
	}

	public List<ProjectTask> getProjectTasks() {
		if(projectTasks==null){
			this.projectTasks=new ArrayList<>();
		}
		return this.projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public ProjectTask addProjectTask(ProjectTask projectTask) {
		getProjectTasks().add(projectTask);
		projectTask.setProjectList(this);

		return projectTask;
	}

	public ProjectTask removeProjectTask(ProjectTask projectTask) {
		getProjectTasks().remove(projectTask);
		projectTask.setProjectList(null);

		return projectTask;
	}

}