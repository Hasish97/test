package com.Inova.ClickupDashboard.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String datecreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	//bi-directional many-to-one association to MetProjectCheckPoint
	@OneToMany(mappedBy="project")
	private List<MetProjectCheckPoint> metProjectCheckPoints;

	@OneToMany(mappedBy="project")
	private List<MstProjectDetail> mstProjectDetails;
	//bi-directional many-to-one association to MetProjectFinaicialCheckPoint
	@OneToMany(mappedBy="project")
	private List<MetProjectFinaicialCheckPoint> metProjectFinaicialCheckPoints;

	@ManyToOne
	private Organization organization;

	//bi-directional many-to-one association to Project_member

	//bi-directional many-to-one association to ProjectFolder
	@OneToMany(mappedBy="project")
	private List<ProjectFolder> projectFolders;

	public Project() {
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

	public List<MetProjectCheckPoint> getMetProjectCheckPoints() {
		if(metProjectCheckPoints==null){
			this.metProjectCheckPoints=new ArrayList<>();
		}
		return this.metProjectCheckPoints;
	}

	public void setMetProjectCheckPoints(List<MetProjectCheckPoint> metProjectCheckPoints) {
		this.metProjectCheckPoints = metProjectCheckPoints;
	}

	public MetProjectCheckPoint addMetProjectCheckPoint(MetProjectCheckPoint metProjectCheckPoint) {
		getMetProjectCheckPoints().add(metProjectCheckPoint);
		metProjectCheckPoint.setProject(this);

		return metProjectCheckPoint;
	}

	public MetProjectCheckPoint removeMetProjectCheckPoint(MetProjectCheckPoint metProjectCheckPoint) {
		getMetProjectCheckPoints().remove(metProjectCheckPoint);
		metProjectCheckPoint.setProject(null);

		return metProjectCheckPoint;
	}

	public List<MetProjectFinaicialCheckPoint> getMetProjectFinaicialCheckPoints() {
		if(metProjectFinaicialCheckPoints==null){
			this.metProjectFinaicialCheckPoints=new ArrayList<>();
		}
		return this.metProjectFinaicialCheckPoints;
	}

	public void setMetProjectFinaicialCheckPoints(List<MetProjectFinaicialCheckPoint> metProjectFinaicialCheckPoints) {
		this.metProjectFinaicialCheckPoints = metProjectFinaicialCheckPoints;
	}

	public MetProjectFinaicialCheckPoint addMetProjectFinaicialCheckPoint(MetProjectFinaicialCheckPoint metProjectFinaicialCheckPoint) {
		getMetProjectFinaicialCheckPoints().add(metProjectFinaicialCheckPoint);
		metProjectFinaicialCheckPoint.setProject(this);

		return metProjectFinaicialCheckPoint;
	}

	public MetProjectFinaicialCheckPoint removeMetProjectFinaicialCheckPoint(MetProjectFinaicialCheckPoint metProjectFinaicialCheckPoint) {
		getMetProjectFinaicialCheckPoints().remove(metProjectFinaicialCheckPoint);
		metProjectFinaicialCheckPoint.setProject(null);

		return metProjectFinaicialCheckPoint;
	}

	public List<MstProjectDetail> getMstProjectDetails() {
		return this.mstProjectDetails;
	}

	public void setMstProjectDetails(List<MstProjectDetail> mstProjectDetails) {
		this.mstProjectDetails = mstProjectDetails;
	}

	public MstProjectDetail addMstProjectDetail(MstProjectDetail mstProjectDetail) {
		getMstProjectDetails().add(mstProjectDetail);
		mstProjectDetail.setProject(this);

		return mstProjectDetail;
	}

	public MstProjectDetail removeMstProjectDetail(MstProjectDetail mstProjectDetail) {
		getMstProjectDetails().remove(mstProjectDetail);
		mstProjectDetail.setProject(null);

		return mstProjectDetail;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<ProjectFolder> getProjectFolders() {
		if(projectFolders==null){
			this.projectFolders=new ArrayList<>();
		}
		return this.projectFolders;
	}

	public void setProjectFolders(List<ProjectFolder> projectFolders) {
		this.projectFolders = projectFolders;
	}

	public ProjectFolder addProjectFolder(ProjectFolder projectFolder) {
		getProjectFolders().add(projectFolder);
		projectFolder.setProject(this);

		return projectFolder;
	}

	public ProjectFolder removeProjectFolder(ProjectFolder projectFolder) {
		getProjectFolders().remove(projectFolder);
		projectFolder.setProject(null);

		return projectFolder;
	}
}