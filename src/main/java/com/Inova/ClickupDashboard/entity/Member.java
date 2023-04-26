package com.Inova.ClickupDashboard.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the member database table.
 * 
 */
@Entity
@NamedQuery(name="Member.findAll", query="SELECT m FROM Member m")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	private Organization organization;

	//bi-directional many-to-one association to Project_member

	@OneToMany(mappedBy="member")
	private List<ProjectTrackIntervel> projectTrackIntervels;

	@OneToMany(mappedBy="member")
	private List<ProjectRole_member> projectRole_members;


	//bi-directional many-to-one association to ProjectFolderSharedmember
	@OneToMany(mappedBy="member")
	private List<ProjectFolderSharedmember> projectFolderSharedmembers;

	//bi-directional many-to-one association to ProjectTaskAssigner
	@OneToMany(mappedBy="member")
	private List<ProjectTaskAssigner> projectTaskAssigners;

	public Member() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}


	public List<ProjectFolderSharedmember> getProjectFolderSharedmembers() {
		if(projectFolderSharedmembers==null){
			projectFolderSharedmembers=new ArrayList<>();
		}
		return this.projectFolderSharedmembers;
	}

	public void setProjectFolderSharedmembers(List<ProjectFolderSharedmember> projectFolderSharedmembers) {
		this.projectFolderSharedmembers = projectFolderSharedmembers;
	}

	public ProjectFolderSharedmember addProjectFolderSharedmember(ProjectFolderSharedmember projectFolderSharedmember) {
		getProjectFolderSharedmembers().add(projectFolderSharedmember);
		projectFolderSharedmember.setMember(this);

		return projectFolderSharedmember;
	}

	public ProjectFolderSharedmember removeProjectFolderSharedmember(ProjectFolderSharedmember projectFolderSharedmember) {
		getProjectFolderSharedmembers().remove(projectFolderSharedmember);
		projectFolderSharedmember.setMember(null);

		return projectFolderSharedmember;
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
		projectTaskAssigner.setMember(this);

		return projectTaskAssigner;
	}

	public ProjectTaskAssigner removeProjectTaskAssigner(ProjectTaskAssigner projectTaskAssigner) {
		getProjectTaskAssigners().remove(projectTaskAssigner);
		projectTaskAssigner.setMember(null);

		return projectTaskAssigner;
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

	public List<ProjectRole_member> getProjectRole_members() {
		if(projectRole_members==null){
			this.projectRole_members=new ArrayList<>();
		}
		return projectRole_members;
	}

	public void setProjectRole_members(List<ProjectRole_member> projectRole_members) {
		this.projectRole_members = projectRole_members;
	}
}