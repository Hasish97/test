package com.Inova.ClickupDashboard.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the project_track_intervel database table.
 * 
 */
@Entity
@Table(name="project_track_intervel")
@NamedQuery(name="ProjectTrackIntervel.findAll", query="SELECT p FROM ProjectTrackIntervel p")
public class ProjectTrackIntervel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String dateadded;

	private String tracktime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utpdateadded;

	private String utptracktime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified_date;

	//bi-directional many-to-one association to ProjectFulltime

	@ManyToOne
	private ProjectTask projectTask;

	@ManyToOne
	private Member member;

	public ProjectTrackIntervel() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateadded() {
		return this.dateadded;
	}

	public void setDateadded(String dateadded) {
		this.dateadded = dateadded;
	}

	public String getTracktime() {
		return this.tracktime;
	}

	public void setTracktime(String tracktime) {
		this.tracktime = tracktime;
	}

	public Date getUtpdateadded() {
		return this.utpdateadded;
	}

	public void setUtpdateadded(Date utpdateadded) {
		this.utpdateadded = utpdateadded;
	}

	public String getUtptracktime() {
		return this.utptracktime;
	}

	public void setUtptracktime(String utptracktime) {
		this.utptracktime = utptracktime;
	}


	public ProjectTask getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
}