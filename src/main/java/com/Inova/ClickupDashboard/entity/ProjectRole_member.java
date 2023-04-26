package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the `project_role_ member` database table.
 * 
 */
@Entity
@Table(name="`project_role_ member`")
@NamedQuery(name="ProjectRole_member.findAll", query="SELECT p FROM ProjectRole_member p")
public class ProjectRole_member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	//bi-directional many-to-one association to Project_role
	@ManyToOne
	@JoinColumn(name="`project role_id`")
	private Project_role projectRole;

	@ManyToOne
	private Member member;

	//bi-directional many-to-one association to Project_member
	@ManyToOne
	private Project project;

	public ProjectRole_member() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project_role getProjectRole() {
		return this.projectRole;
	}

	public void setProjectRole(Project_role projectRole) {
		this.projectRole = projectRole;
	}


	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}