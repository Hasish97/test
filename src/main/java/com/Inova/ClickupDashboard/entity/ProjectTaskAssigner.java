package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the project_task_assigner database table.
 * 
 */
@Entity
@Table(name="project_task_assigner")
@NamedQuery(name="ProjectTaskAssigner.findAll", query="SELECT p FROM ProjectTaskAssigner p")
public class ProjectTaskAssigner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	//bi-directional many-to-one association to Member
	@ManyToOne
	private Member member;

	//bi-directional many-to-one association to ProjectTask
	@ManyToOne
	private ProjectTask projectTask;

	public ProjectTaskAssigner() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public ProjectTask getProjectTask() {
		return this.projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}

}