package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the project_folder_sharedmember database table.
 * 
 */
@Entity
@Table(name="project_folder_sharedmember")
@NamedQuery(name="ProjectFolderSharedmember.findAll", query="SELECT p FROM ProjectFolderSharedmember p")
public class ProjectFolderSharedmember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	//bi-directional many-to-one association to ProjectFolder
	@ManyToOne
	private ProjectFolder projectFolder;

	//bi-directional many-to-one association to Member
	@ManyToOne
	private Member member;

	public ProjectFolderSharedmember() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public ProjectFolder getProjectFolder() {
		return this.projectFolder;
	}

	public void setProjectFolder(ProjectFolder projectFolder) {
		this.projectFolder = projectFolder;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}