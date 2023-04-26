package com.Inova.ClickupDashboard.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project_task database table.
 * 
 */
@Entity
@Table(name="project_task")
@NamedQuery(name="ProjectTask.findAll", query="SELECT p FROM ProjectTask p")
public class ProjectTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String datecreated;

	private String duedate;

	private String time_spent;
	private int level;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	private String parendtadkid;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utpdatecreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utpduedate;

	//bi-directional many-to-one association to ProjectFulltime

	@OneToMany(mappedBy="projectTask")
	private List<ProjectTrackIntervel> projectTrackIntervels;

	//bi-directional many-to-one association to ProjectList
	@ManyToOne
	private ProjectList projectList;

	@ManyToOne
	private Project project;

	//bi-directional many-to-one association to ProjectTaskAssigner
	@OneToMany(mappedBy="projectTask")
	private List<ProjectTaskAssigner> projectTaskAssigners;

	public ProjectTask() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(String datecreated) {
		this.datecreated = datecreated;
	}

	public String getDuedate() {
		return this.duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getTime_spent() {
		return time_spent;
	}

	public void setTime_spent(String time_spent) {
		this.time_spent = time_spent;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public String getParendtadkid() {
		return this.parendtadkid;
	}

	public void setParendtadkid(String parendtadkid) {
		this.parendtadkid = parendtadkid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUtpdatecreated() {
		return this.utpdatecreated;
	}

	public void setUtpdatecreated(Date utpdatecreated) {
		this.utpdatecreated = utpdatecreated;
	}

	public Date getUtpduedate() {
		return this.utpduedate;
	}

	public void setUtpduedate(Date utpduedate) {
		this.utpduedate = utpduedate;
	}


	public List<ProjectTrackIntervel> getProjectTrackIntervels() {
		if(projectTrackIntervels==null){
			this.projectTrackIntervels=new ArrayList<>();
		}
		return projectTrackIntervels;
	}

	public void setProjectTrackIntervels(List<ProjectTrackIntervel> projectTrackIntervels) {
		this.projectTrackIntervels = projectTrackIntervels;
	}

	public ProjectList getProjectList() {
		return this.projectList;
	}

	public void setProjectList(ProjectList projectList) {
		this.projectList = projectList;
	}

	public List<ProjectTaskAssigner> getProjectTaskAssigners() {
		if(projectTaskAssigners==null){
			this.projectTaskAssigners=new ArrayList<>();
		}
		return this.projectTaskAssigners;
	}

	public void setProjectTaskAssigners(List<ProjectTaskAssigner> projectTaskAssigners) {
		this.projectTaskAssigners = projectTaskAssigners;
	}

	public ProjectTaskAssigner addProjectTaskAssigner(ProjectTaskAssigner projectTaskAssigner) {
		getProjectTaskAssigners().add(projectTaskAssigner);
		projectTaskAssigner.setProjectTask(this);

		return projectTaskAssigner;
	}

	public ProjectTaskAssigner removeProjectTaskAssigner(ProjectTaskAssigner projectTaskAssigner) {
		getProjectTaskAssigners().remove(projectTaskAssigner);
		projectTaskAssigner.setProjectTask(null);

		return projectTaskAssigner;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}